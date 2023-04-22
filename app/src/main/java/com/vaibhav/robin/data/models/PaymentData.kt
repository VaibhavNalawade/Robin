package com.vaibhav.robin.data.models

import androidx.annotation.Keep
import java.util.UUID
@Keep
data class PaymentData(
    val id:String=UUID.randomUUID().toString(),
    val pan:String="",
    val cvv:Int?=null,
    val expiryDate:String="",
    val cardHolderName:String=""
)
