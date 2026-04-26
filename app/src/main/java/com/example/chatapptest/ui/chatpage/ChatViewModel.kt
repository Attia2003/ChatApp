package com.example.chatapptest.ui.chatpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.R
import com.example.chatapptest.data.session.SessionManager
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.database.model.MessageData
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.domain.usecase.message.ObserveMessagesUseCase
import com.example.chatapptest.domain.usecase.message.SendMessageUseCase
import com.example.chatapptest.util.SingleLiveEvent
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    var room: RommData? = null
        private set

    val messageuserlivedata = MutableLiveData("")
    val ToastLiveData = SingleLiveEvent<String>()
    val messagesLiveData = MutableLiveData<List<MessageData>>(emptyList())
    val roomTitle = MutableLiveData("")
    val roomStatus = MutableLiveData("")
    val roomAvatarRes = MutableLiveData(R.drawable.ic_chat_empty)
    val isInitialLoading = MutableLiveData(true)
    val isSending = MutableLiveData(false)
    val canSendMessage = MutableLiveData(false)
    val showTypingIndicator = MutableLiveData(false)
    val inlineErrorMessage = MutableLiveData<String?>(null)
    val showEmptyState = MutableLiveData(false)
    val showErrorState = MutableLiveData(false)
    val showInlineErrorChip = MutableLiveData(false)

    private var hasMessages = false
    private var observeMessagesJob: Job? = null

    private val draftObserver = Observer<String?> {
        updateComposerState()
        updateStateVisibility()
    }
    private val sendingObserver = Observer<Boolean> {
        updateComposerState()
    }

    init {
        messageuserlivedata.observeForever(draftObserver)
        isSending.observeForever(sendingObserver)
        updateComposerState()
        updateStateVisibility()
    }

    val currentUserId: String?
        get() = sessionManager.currentUser.value?.id

    fun sendmessage() {
        if (isSending.value == true) return

        val content = messageuserlivedata.value?.trim()
        val currentUser = sessionManager.currentUser.value
        val roomId = room?.id

        if (content.isNullOrBlank()) return
        if (currentUser?.id.isNullOrBlank() || roomId.isNullOrBlank()) {
            ToastLiveData.value = "Unable to send message right now"
            return
        }

        val message = MessageData(
            content = content,
            timestamp = Timestamp.now(),
            senderId = currentUser?.id,
            senderName = currentUser?.userName,
            roomID = roomId,
        )

        isSending.value = true
        inlineErrorMessage.value = null
        updateStateVisibility()

        viewModelScope.launch {
            val result = sendMessageUseCase(message)
            isSending.value = false
            if (result.isSuccess) {
                messageuserlivedata.value = ""
            } else {
                val errorMessage = "Message failed to send. Check your network and try again."
                inlineErrorMessage.value = errorMessage
                updateStateVisibility()
                ToastLiveData.value = errorMessage
            }
        }
    }

    fun changeroom(room: RommData?) {
        this.room = room
        roomTitle.value = room?.title?.trim().orEmpty()
        roomStatus.value = room?.decription?.trim().orEmpty()
        roomAvatarRes.value = CategoryData.getcatogriesbyid(room?.categoryid)
        updateComposerState()
        listentomessages()
    }

    fun retryLoading() {
        if (room?.id.isNullOrBlank()) {
            ToastLiveData.value = "Unable to reload this chat right now"
            return
        }
        listentomessages()
    }

    private fun listentomessages() {
        observeMessagesJob?.cancel()

        val roomId = room?.id
        if (roomId.isNullOrBlank()) {
            messagesLiveData.value = emptyList()
            hasMessages = false
            isInitialLoading.value = false
            inlineErrorMessage.value = "Conversation details are unavailable right now."
            updateStateVisibility()
            return
        }

        if (!hasMessages) {
            isInitialLoading.value = true
        }
        inlineErrorMessage.value = null
        updateStateVisibility()

        observeMessagesJob = viewModelScope.launch {
            observeMessagesUseCase(roomId)
                .catch { error ->
                    isInitialLoading.value = false
                    inlineErrorMessage.value =
                        "Unable to load messages. Check your connection and try again."
                    updateStateVisibility()
                    ToastLiveData.value =
                        "Error loading messages: ${error.localizedMessage ?: "Unknown error"}"
                }
                .collect { messages ->
                    messagesLiveData.value = messages
                    hasMessages = messages.isNotEmpty()
                    isInitialLoading.value = false
                    inlineErrorMessage.value = null
                    updateStateVisibility()
                }
        }
    }

    private fun updateComposerState() {
        val hasDraft = !messageuserlivedata.value.isNullOrBlank()
        val readyToSend = hasDraft && isSending.value != true && !room?.id.isNullOrBlank()
        showTypingIndicator.value = hasDraft && isSending.value != true
        canSendMessage.value = readyToSend
    }

    private fun updateStateVisibility() {
        val hasError = !inlineErrorMessage.value.isNullOrBlank()
        val hasDraft = !messageuserlivedata.value.isNullOrBlank()
        val loading = isInitialLoading.value == true

        showErrorState.value = !loading && hasError && !hasMessages
        showInlineErrorChip.value = !loading && hasError && hasMessages
        showEmptyState.value = !loading && !hasError && !hasMessages && !hasDraft
    }

    override fun onCleared() {
        messageuserlivedata.removeObserver(draftObserver)
        isSending.removeObserver(sendingObserver)
        observeMessagesJob?.cancel()
        super.onCleared()
    }
}
