package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.MessageData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : MessageRepository {

    private fun getMessagesCollection(roomId: String) =
        firestore.collection("rooms")
            .document(roomId)
            .collection(MessageData.CollectionMessageName)

    override suspend fun sendMessage(message: MessageData): Result<MessageData> {
        return try {
            val docRef = getMessagesCollection(message.roomID ?: "").document()
            val messageWithId = message.copy(id = docRef.id)
            docRef.set(messageWithId).await()
            Result.success(messageWithId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getMessagesFlow(roomId: String): Flow<List<MessageData>> = callbackFlow {
        val listener = getMessagesCollection(roomId)
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                
                if (snapshot != null) {
                    val messages = snapshot.toObjects(MessageData::class.java)
                    trySend(messages)
                }
            }
        
        awaitClose { listener.remove() }
    }
}
