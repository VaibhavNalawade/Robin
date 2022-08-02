package com.vaibhav.robin.domain

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.auth.Auth
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class SignUpValidation(private val postValidate: () -> Unit) {
    suspend fun validate(
        signUpEmail: MutableState<TextFieldState>,
        signUpPassword: MutableState<TextFieldState>,
        signUpConfirmPassword: MutableState<TextFieldState>,
        authState: MutableStateFlow<AuthState>
    ) {
        signUpEmail.value = Validators.email(signUpEmail.value)
        signUpPassword.value = Validators.password(signUpPassword.value)
        signUpConfirmPassword.value =
            Validators.confirmPassword(signUpConfirmPassword.value, signUpPassword.value)

        if (
            (!signUpEmail.value.error) &&
            (!signUpPassword.value.error) &&
            (!signUpConfirmPassword.value.error)
        ) Auth.createUser(signUpEmail.value.text,signUpPassword.value.text,authState)
        else authState.value=AuthState.Init
    }
}