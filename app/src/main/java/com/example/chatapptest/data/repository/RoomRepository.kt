package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.RommData

interface RoomRepository {
    suspend fun createRoom(
        title: String,
        description: String,
        categoryId: Int,
        adminId: String
    ): Result<RommData>
    
    suspend fun getAllRooms(): Result<List<RommData>>
    suspend fun getRoomById(roomId: String): Result<RommData>
}
