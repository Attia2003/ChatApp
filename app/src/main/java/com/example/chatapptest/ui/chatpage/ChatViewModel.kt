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
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class ChatViewModel  : ViewModel(){

    var room: RommData?=null
    val messageuserlivedata = MutableLiveData<String>()
    val ToastLiveData = SingleLiveEvent<String>()
    val newmessagelivedata = SingleLiveEvent<List<MessageData>>()

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
    fun changeroom(room :RommData?){
        this.room = room
        listentomessages()

    }

    fun listentomessages(){
        MessageDao.getmessagecollec(room?.id?:"")
            .addSnapshotListener(EventListener { value, error ->
//                    value?.documents?.forEach {
//                        val message = it.toObject(MessageData::class.java) // bt work on all doc return
//                    }

                    val newmessages = mutableListOf<MessageData>()
                    // bt work on single doc return (be check 3la el be7sl le change )
                    value?.documentChanges?.forEach {
                            if (it.type == DocumentChange.Type.ADDED){
                               val message =  it.document.toObject(MessageData::class.java)
                                newmessages.add(message)
                            }else if (it.type == DocumentChange.Type.MODIFIED){

                            }else if (it.type == DocumentChange.Type.REMOVED)
                           it.type
                    }
                    newmessagelivedata.value = newmessages
            }

            )

    }

}