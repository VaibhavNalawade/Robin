package com.vaibhav.robin.auth

import com.google.firebase.auth.AuthResult

sealed class AuthState{
    data class Successful(val authResult:AuthResult) : AuthState()
    data class Loading(val progress:Int=-1):AuthState()
    data class Error(val exception:Exception):AuthState()
    object Init : AuthState()
}
