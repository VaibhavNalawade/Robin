package com.vaibhav.robin.entities.remote.signup


data class AccountDetails(
    val contactInformation: ContactInformation = ContactInformation(),
    val personalDetails:Name=Name()
)
