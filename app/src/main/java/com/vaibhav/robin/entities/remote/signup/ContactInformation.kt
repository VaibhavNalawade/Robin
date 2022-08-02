package com.vaibhav.robin.entities.remote.signup

data class ContactInformation(
    val email:String="",
    val phone:String="",
    val address: Address = Address()
)
