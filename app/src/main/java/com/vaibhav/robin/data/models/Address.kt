package com.vaibhav.robin.data.models

data class Address(
    val id:String="",
    val fullName:String="",
    val streetAddress:String="",
    val apartmentAddress:String?=null,
    val city:String="",
    val state:String?=null,
    val pinCode:String="",
    val phoneNumber:String=""
)
