package com.vaibhav.robin.domain.use_case.auth

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInWithEmailPassword @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        emailState: MutableState<TextFieldState>,
        passwordState: MutableState<TextFieldState>,
    ): Flow<Response<Boolean>> {
        emailState.value = Validators.email(emailState.value)
        passwordState.value = Validators.password(passwordState.value)
        return if (!emailState.value.error && !passwordState.value.error)
            repository.signInWithEmailPassword(
                emailState.value.text,
                passwordState.value.text
            )
        else flow { emit(Response.Error(ValidationFailedException())) }
    }
}
