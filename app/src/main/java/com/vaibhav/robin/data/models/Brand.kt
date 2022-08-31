package com.vaibhav.robin.data.models

import com.google.firebase.firestore.DocumentReference

data class Brand(
    val name: String="",
    val url: String="",
    val ref: DocumentReference?=null
)