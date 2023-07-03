package com.vaibhav.robin.domain.model

import android.net.Uri

data class ProfileData(
    val name:String?,
    val image:Uri?,
    val email:String?,
    val userIsVerified:Boolean,
    val phone:String?,
    val uid:String
)