package com.example.chatapptest.domain.usecase.room

import com.example.chatapptest.data.repository.RoomRepository
import com.example.chatapptest.database.model.RommData
import javax.inject.Inject

class CreateRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        categoryId: Int,
        adminId: String
    ): Result<RommData> {
        return roomRepository.createRoom(title, description, categoryId, adminId)
    }
}
