package com.example.chatapptest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.domain.usecase.auth.LogoutUseCase
import com.example.chatapptest.domain.usecase.room.GetRoomsUseCase
import com.example.chatapptest.ui.Error.ViewEror
import com.example.chatapptest.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    
    val event = SingleLiveEvent<HomeViewEvent>()
    val messageLiveData = SingleLiveEvent<ViewEror>()
    val roomsLiveData = MutableLiveData<List<RommData>>()

    fun GoToAddRoom() {
        event.postValue(HomeViewEvent.NavigateToAddRoom)
    }
    
    fun loadrooms() {
        viewModelScope.launch {
            val result = getRoomsUseCase()
            if (result.isSuccess) {
                val rooms = result.getOrNull() ?: emptyList()
                roomsLiveData.postValue(rooms)
            } else {
                // Optionally show error
                val error = result.exceptionOrNull()
                messageLiveData.postValue(
                    ViewEror(message = error?.localizedMessage ?: "Failed to load rooms")
                )
            }
        }
    }

    fun Logout() {
        messageLiveData.postValue(
            ViewEror(
                message = "Are you sure you want to log out?",
                psoActionName = "OK",
                posActionClick = {
                    logoutUseCase()
                    event.postValue(HomeViewEvent.NavigateToLogin)
                },
                negActionName = "Cancel"
            )
        )
    }
}