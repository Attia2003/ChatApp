package com.example.chatapptest.domain.usecase.message

import com.example.chatapptest.data.repository.MessageRepository
import com.example.chatapptest.database.model.MessageData
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(message: MessageData): Result<MessageData> {
        return messageRepository.sendMessage(message)
    }
}
