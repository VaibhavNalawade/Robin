package com.vaibhav.robin.domain

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.auth.Auth
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

class LoginValidation(private val authState: MutableStateFlow<AuthState>) {

    suspend fun validate(emailTextFieldState: MutableState<TextFieldState>, passwordTextFieldState: MutableState<TextFieldState>){
        emailTextFieldState.value = Validators.email(emailTextFieldState.value)
        passwordTextFieldState.value = Validators.password(passwordTextFieldState.value)
        if (!emailTextFieldState.value.error||!passwordTextFieldState.value.error)
            Auth.signInWithEmail(emailTextFieldState.value.text,passwordTextFieldState.value.text,authState)

        else{
            authState.value = AuthState.Error(Exception("Invalid credentials"))
        }
    }
}