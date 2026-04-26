package com.example.chatapptest.domain.usecase.message

import com.example.chatapptest.data.repository.MessageRepository
import com.example.chatapptest.database.model.MessageData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    operator fun invoke(roomId: String): Flow<List<MessageData>> {
        return messageRepository.getMessagesFlow(roomId)
    }
}
