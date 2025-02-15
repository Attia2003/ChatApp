package com.example.chatapptest

import com.example.chatapptest.database.model.UserData
import com.google.firebase.firestore.auth.User

object SessionProvider {
    var user : UserData? = null
}