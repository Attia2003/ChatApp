package com.example.chatapptest.database.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class MessageData(
    var id: String? = null,
    var content: String? = null,
    var timestamp: Timestamp? = null,
    var senderName: String? = null,
    var senderId: String? = null,
    var roomID: String? = null
)
{
    companion object{
        const val CollectionMessageName = "messages"
    }

    fun formattime() : String{
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

         return simpleDateFormat.format(timestamp?.toDate())
    }
}



 