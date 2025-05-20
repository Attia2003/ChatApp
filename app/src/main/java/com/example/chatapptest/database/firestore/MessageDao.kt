package com.example.chatapptest.database.firestore

import com.example.chatapptest.database.model.MessageData
import com.example.chatapptest.database.model.MessageData.Companion.CollectionMessageName
import com.google.android.gms.tasks.OnCompleteListener

object MessageDao {

    fun sendmessage(message : MessageData, completelistner : OnCompleteListener<Void>){
        val messageDoc = getmessagecollec(message.roomID?:"")
            .document()
        message.id = messageDoc.id
        messageDoc.set(message).addOnCompleteListener(completelistner)
    }


    fun getmessagecollec(roomId : String)=
        RoomDao.getRoom().document(roomId)
            .collection(CollectionMessageName)





}