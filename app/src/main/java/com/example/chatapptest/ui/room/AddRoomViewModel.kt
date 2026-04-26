package com.example.chatapptest.ui.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.data.session.SessionManager
import com.example.chatapptest.database.model.CategoryData
import com.example.chatapptest.domain.usecase.room.CreateRoomUseCase
import com.example.chatapptest.ui.Error.ViewEror
import com.example.chatapptest.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRoomViewModel @Inject constructor(
    private val createRoomUseCase: CreateRoomUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {
    
    val event = SingleLiveEvent<AddRoomEvent>()

    val roomName = MutableLiveData<String>()
    val roomDescription = MutableLiveData<String>()
    val roomNameErors = MutableLiveData<String?>()
    val roomDescriptionErors = MutableLiveData<String?>()

    val messageLiveData = SingleLiveEvent<ViewEror>()

    val categories = CategoryData.getCatogries()
    var selectedCategory = categories[0]
    val isCreatingRoom = MutableLiveData<Boolean>(false)

    fun createRoom() {
        if (!Isvalidte()) {
            return
        }

        if (isCreatingRoom.value == true) {
            return
        }

        isCreatingRoom.postValue(true)

        viewModelScope.launch {
            val result = createRoomUseCase(
                title = roomName.value ?: "",
                description = roomDescription.value ?: "",
                categoryId = selectedCategory.id,
                adminId = sessionManager.currentUser.value?.id ?: ""
            )

            isCreatingRoom.postValue(false)

            if (result.isSuccess) {
                messageLiveData.postValue(
                    ViewEror(
                        message = "Room created successfully!",
                        psoActionName = "OK",
                        posActionClick = {
                            event.postValue(AddRoomEvent.NavigateToHome)
                        },
                        isCancelable = false
                    )
                )
            } else {
                val error = result.exceptionOrNull()
                messageLiveData.postValue(
                    ViewEror(message = error?.localizedMessage ?: "Failed to create room")
                )
            }
        }
    }

    fun Isvalidte(): Boolean {
        var isvalidd = true
        if (roomName.value.isNullOrEmpty()) {
            roomNameErors.postValue("Room Name is Required")
            isvalidd = false
        } else {
            roomNameErors.postValue(null)
        }
        if (roomDescription.value.isNullOrEmpty()) {
            roomDescriptionErors.postValue("Room Description is Required")
            isvalidd = false
        } else {
            roomDescriptionErors.postValue(null)
        }
        return isvalidd
    }

    fun categorySelected(position: Int) {
        selectedCategory = categories[position]
    }
}