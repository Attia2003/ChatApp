package com.example.chatapptest.ui.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.util.SingleLiveEvent

class AddRoomViewModel: ViewModel()  {
        val event = SingleLiveEvent<AddRoomEvent>()

        val roomName = MutableLiveData<String>()
        val roomDescription = MutableLiveData<String>()

        fun createRoom(){

        }



}