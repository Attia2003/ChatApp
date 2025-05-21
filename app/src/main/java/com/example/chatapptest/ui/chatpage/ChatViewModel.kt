package com.example.chatapptest.ui.chatpage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapptest.SessionProvider
import com.example.chatapptest.database.firestore.MessageDao
import com.example.chatapptest.database.firestore.RoomDao
import com.example.chatapptest.database.model.MessageData
import com.example.chatapptest.database.model.RommData
import com.example.chatapptest.util.SingleLiveEvent
import com.google.firebase.Timestamp

class ChatViewModel  : ViewModel(){

    var room: RommData?=null
    val messageuserlivedata = MutableLiveData<String>()
    val ToastLiveData = SingleLiveEvent<String>()


    fun sendmessage(){
        if (messageuserlivedata.value.isNullOrBlank()) return
        val message = MessageData(
            content = messageuserlivedata.value,
            timestamp = Timestamp.now(),
            senderId = SessionProvider.user?.userName,
            senderName = SessionProvider.user?.id ,
            roomID =   room?.id,
        )
        MessageDao.sendmessage(message){
            if (it.isSuccessful){
                messageuserlivedata.value = ""
                return@sendmessage
            }
            ToastLiveData.value = "Something Went Wrong , Check Ur Network "
        }

    }



}