package com.example.chatapptest.domain.usecase.auth

import com.example.chatapptest.data.repository.UserRepository
import com.example.chatapptest.data.session.SessionManager
import com.example.chatapptest.database.model.UserData
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<UserData> {
        return try {
            val displayName = listOf(firstName, lastName)
                .map(String::trim)
                .filter(String::isNotEmpty)
                .joinToString(" ")

            val userData = UserData(
                userName = displayName,
                email = email
            )

            val result = userRepository.registerUser(email, password, userData)
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    sessionManager.saveUser(user)
                }
            }
            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
