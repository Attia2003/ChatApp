package com.example.chatapptest.data.repository

import com.example.chatapptest.database.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : UserRepository {

    override suspend fun createUser(user: UserData): Result<Unit> {
        return try {
            firestore.collection("users")
                .document(user.id ?: "")
                .set(user)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(uid: String): Result<UserData> {
        return try {
            val snapshot = firestore.collection("users")
                .document(uid)
                .get()
                .await()
            
            if (snapshot.exists()) {
                val user = snapshot.toObject(UserData::class.java)
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Failed to parse user data"))
                }
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("Login failed: UID is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
        userData: UserData
    ): Result<UserData> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
            if (uid != null) {
                val user = userData.copy(id = uid)
                createUser(user).getOrThrow()
                Result.success(user)
            } else {
                Result.failure(Exception("Registration failed: UID is null"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
