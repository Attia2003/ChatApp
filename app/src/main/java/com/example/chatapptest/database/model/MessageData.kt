package com.example.chatapptest.database.model

import com.google.firebase.Timestamp

data class MessageData(
    var id : String ?=null,
    val content : String ? = null,
    val timestamp : Timestamp,
    val senderName  : String?=null,
    val senderId : String?=null,
    val roomID : String?=null
)
{
    companion object{
        const val CollectionMessageName = "messages"
    }

    fun formattime(){

    }

}
 