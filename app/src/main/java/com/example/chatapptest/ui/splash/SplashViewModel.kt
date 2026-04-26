package com.example.chatapptest.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.data.session.SessionManager
import com.example.chatapptest.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    
    val event = SingleLiveEvent<SplashViewEvent>()

    fun Navigate() {
        viewModelScope.launch {

            delay(1000)
            
            if (sessionManager.isLoggedIn()) {
                event.postValue(SplashViewEvent.NavigateToHome)
            } else {
                event.postValue(SplashViewEvent.NavigateTologin)
            }
        }
    }
}