package com.vaibhav.robin.data.models

import com.google.firebase.firestore.DocumentReference

data class QueryProduct(
    val brandDocumentRef:List<String>?,
    val category:List<String>?,
    val name:String?,
    val minPrice:Int?,
    val MaxPrice:Int?,
)
