package com.example.chatapptest.domain.usecase.auth

import com.example.chatapptest.data.repository.UserRepository
import com.example.chatapptest.data.session.SessionManager
import com.example.chatapptest.database.model.UserData
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(email: String, password: String): Result<UserData> {
        return try {
            // Step 1: Authenticate with Firebase
            val uidResult = userRepository.loginUser(email, password)
            if (uidResult.isFailure) {
                return Result.failure(uidResult.exceptionOrNull() ?: Exception("Login failed"))
            }
            
            val uid = uidResult.getOrNull() ?: return Result.failure(Exception("UID is null"))
            
            // Step 2: Fetch user data from Firestore
            val userResult = userRepository.getUserById(uid)
            if (userResult.isFailure) {
                return Result.failure(userResult.exceptionOrNull() ?: Exception("Failed to fetch user data"))
            }
            
            val user = userResult.getOrNull() ?: return Result.failure(Exception("User data is null"))
            
            // Step 3: Save to session
            sessionManager.saveUser(user)
            
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
