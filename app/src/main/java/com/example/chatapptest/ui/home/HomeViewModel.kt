package com.example.chatapptest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val event = MutableLiveData<HomeViewEvent>()

    fun GoToAddRoom(){
            event.postValue(HomeViewEvent.NavigateToAddRoom)

    }

}