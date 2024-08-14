package com.vaibhav.robin.domain.model

import android.net.Uri

data class CurrentUserProfileData(
    val userAuthenticated:Boolean=false,
    val name:String?=null,
    val image:Uri?=null,//TODO Need to Reference Device Resource for Guest Users
    val email:String?=null,
    val userIsVerified:Boolean=false,
    val phone:String?=null,
    val uid:String="GuestUSER"
)