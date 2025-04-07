package com.example.chatapptest.ui.splash

import FireStoreUserDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.firestore.RoomDao
import com.example.chatapptest.database.model.UserData
import com.example.chatapptest.util.SingleLiveEvent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    val  event = SingleLiveEvent<SplashViewEvent>()

    fun Navigate() {
        viewModelScope.launch {
            if (Firebase.auth.currentUser == null) {
                event.postValue(SplashViewEvent.NavigateTologin)
                return@launch
            }

            try {
                val snapshot = FireStoreUserDao.getuserbyid(Firebase.auth.currentUser?.uid ?: "")
                val user = snapshot?.toObject(UserData::class.java)
                SessionProvider.user = user

                if (user == null) {
                    event.postValue(SplashViewEvent.NavigateTologin)
                } else {
                    event.postValue(SplashViewEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                event.postValue(SplashViewEvent.NavigateTologin)
            }
        }
    }
}