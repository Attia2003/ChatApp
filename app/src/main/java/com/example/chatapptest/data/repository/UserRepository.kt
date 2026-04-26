package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.UserData

interface UserRepository {
    suspend fun createUser(user: UserData): Result<Unit>
    suspend fun getUserById(uid: String): Result<UserData>
    suspend fun loginUser(email: String, password: String): Result<String>
    suspend fun registerUser(email: String, password: String, userData: UserData): Result<UserData>
}
