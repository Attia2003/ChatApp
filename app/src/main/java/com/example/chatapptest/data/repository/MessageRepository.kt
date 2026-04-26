package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.MessageData
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(message: MessageData): Result<MessageData>
    fun getMessagesFlow(roomId: String): Flow<List<MessageData>>
}
