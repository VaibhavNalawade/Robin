package com.vaibhav.robin.data.models

import java.util.UUID

data class PaymentData(
    val id:String=UUID.randomUUID().toString(),
    val prn:String="",
    val cvv:Int?=null,
    val expiryDate:String="",
    val cardHolderName:String=""
)
