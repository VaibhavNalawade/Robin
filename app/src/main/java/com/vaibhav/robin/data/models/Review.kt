package com.vaibhav.robin.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Review(
    val userName:String="",
    val profileImage:String="",
    val timeStamp:Timestamp= Timestamp.now(),
    val helpfulCount:Int=0,
    val content:String="",
    val rating:Int=0,
    val commentRef:DocumentReference?=null
)
