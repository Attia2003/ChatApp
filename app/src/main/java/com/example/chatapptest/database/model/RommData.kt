package com.example.chatapptest.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RommData (
    val id:String?=null,
    val title:String?=null,
    val decription:String?=null,
    val categoryid:Int?=null,
    val adminId:String?=null,

):Parcelable