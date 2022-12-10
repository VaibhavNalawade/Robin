package com.vaibhav.robin.domain.model

import android.net.Uri
import com.google.firebase.auth.UserInfo

data class ProfileData(
    val name:String?,
    val image:Uri?,
    val email:String?,
    val userIsVerified:Boolean,
    val phone:String?,
)