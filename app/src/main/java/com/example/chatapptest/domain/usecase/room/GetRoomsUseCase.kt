package com.example.chatapptest.domain.usecase.room

import com.example.chatapptest.data.repository.RoomRepository
import com.example.chatapptest.database.model.RommData
import javax.inject.Inject

class GetRoomsUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): Result<List<RommData>> {
        return roomRepository.getAllRooms()
    }
}
