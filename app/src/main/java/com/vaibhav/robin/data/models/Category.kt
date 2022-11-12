package com.vaibhav.robin.data.models

import com.google.firebase.firestore.DocumentReference

data class Category(
    val name:String?=null,
    val ref: DocumentReference?=null
)