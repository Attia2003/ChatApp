package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.RommData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : RoomRepository {

    private fun getRoomsCollection() = firestore.collection("rooms")

    override suspend fun createRoom(
        title: String,
        description: String,
        categoryId: Int,
        adminId: String
    ): Result<RommData> {
        return try {
            val docRef = getRoomsCollection().document()
            val room = RommData(
                id = docRef.id,
                title = title,
                decription = description,
                categoryid = categoryId,
                adminId = adminId
            )
            docRef.set(room).await()
            Result.success(room)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllRooms(): Result<List<RommData>> {
        return try {
            val snapshot = getRoomsCollection().get().await()
            val rooms = snapshot.toObjects(RommData::class.java)
            Result.success(rooms)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRoomById(roomId: String): Result<RommData> {
        return try {
            val snapshot = getRoomsCollection().document(roomId).get().await()
            if (snapshot.exists()) {
                val room = snapshot.toObject(RommData::class.java)
                if (room != null) {
                    Result.success(room)
                } else {
                    Result.failure(Exception("Failed to parse room data"))
                }
            } else {
                Result.failure(Exception("Room not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
