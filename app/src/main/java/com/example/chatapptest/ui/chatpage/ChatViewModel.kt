package com.example.chatapptest.ui.chatpage

import androidx.lifecycle.ViewModel
import com.example.chatapptest.database.firestore.RoomDao
import com.example.chatapptest.database.model.RommData

class ChatViewModel  : ViewModel(){

    var room: RommData?=null
}