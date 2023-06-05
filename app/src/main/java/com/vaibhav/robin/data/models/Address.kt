package com.vaibhav.robin.data.models

import androidx.annotation.Keep
import java.util.UUID

@Keep
data class Address(
    val id:String=UUID.randomUUID().toString(),
    val fullName:String="",
    val streetAddress:String="",
    val apartmentAddress:String?=null,
    val city:String="",
    val state:String?=null,
    val pinCode:String="",
    val phoneNumber:String=""
)
