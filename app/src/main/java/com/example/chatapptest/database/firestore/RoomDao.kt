package com.example.chatapptest.database.firestore

import com.example.chatapptest.database.model.RommData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object RoomDao {
    fun getRoom() :CollectionReference{
       return Firebase.firestore.collection("rooms")

    }
    fun creatRoom(
        title: String,
        decription: String,
        categoryid: Int,
        adminId: String,
        onCompleteListener: OnCompleteListener<Void>
    ) {

        val collection = getRoom()
        val docrRef = collection.document()

           val room = RommData(
               id = docrRef.id,
               title = title,
               decription = decription,
               categoryid = categoryid,
               adminId = adminId

           )

            docrRef.set(room).addOnCompleteListener(onCompleteListener)
    }
}