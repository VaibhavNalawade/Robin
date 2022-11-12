package com.vaibhav.robin.data.models

import com.google.firebase.firestore.DocumentReference

data class Reference(
    val name:String="",
    val reference :DocumentReference?=null,
    val image:String=""
)