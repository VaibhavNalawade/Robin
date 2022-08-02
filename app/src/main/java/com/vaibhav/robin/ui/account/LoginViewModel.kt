package com.vaibhav.robin.ui.account

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.domain.LoginValidation
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Init)
    val authState = _authState

    private val _email = mutableStateOf(TextFieldState())
    val email get() = _email

    private val _password = mutableStateOf(TextFieldState())
    val password get() = _password


    fun signWithEmailPassword() {
        viewModelScope.launch {
            LoginValidation(_authState).validate(_email,_password)
            Log.e("Login ViewModel","f")
        }
    }
}