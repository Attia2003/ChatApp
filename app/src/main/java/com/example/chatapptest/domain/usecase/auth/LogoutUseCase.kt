package com.example.chatapptest.domain.usecase.auth

import com.example.chatapptest.data.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val sessionManager: SessionManager,
    private val auth: FirebaseAuth
) {
    operator fun invoke() {
        auth.signOut()
        sessionManager.clearUser()
    }
}
