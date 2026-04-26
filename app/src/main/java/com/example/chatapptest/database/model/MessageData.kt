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
        val date = timestamp?.toDate() ?: return ""
        val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return simpleDateFormat.format(date)
    }

    fun senderDisplayName(): String {
        return senderName?.trim().orEmpty().ifBlank { "Guest" }
    }

    fun senderInitial(): String {
        return senderDisplayName().firstOrNull()?.toString()?.uppercase(Locale.getDefault()) ?: "?"
    }

    fun contentText(): String {
        return content?.trim().orEmpty()
    }

    fun accessibilityLabel(): String {
        return listOf(senderDisplayName(), contentText(), formattime())
            .filter { it.isNotBlank() }
            .joinToString(", ")
    }
}



 
