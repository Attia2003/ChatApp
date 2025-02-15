package com.example.chatapptest.database.firestore

import com.example.chatapptest.database.model.UserData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FireStoreUserDao {
    public fun getusercollectiion(): CollectionReference {
        val database = Firebase.firestore
         return  database.collection("users")
    }

    fun createuser(user : UserData , onCompleteListener: OnCompleteListener<Void>) {

        val DocRef = getusercollectiion().document(user.id?:"")
           DocRef.set(user).addOnCompleteListener(onCompleteListener)
    }
    fun getuserbyid(uid: String?, onCompleteListener: OnCompleteListener<DocumentSnapshot>){
        getusercollectiion().document(uid?:"")
            .get().addOnCompleteListener(onCompleteListener)

    }
}