package com.vaibhav.robin.domain.use_case.auth

import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpWithEmailPassword @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        emailState: MutableState<TextFieldState>,
        passwordState: MutableState<TextFieldState>,
        confirmPasswordState: MutableState<TextFieldState>,
        nameState:MutableState<TextFieldState>
    ): Flow<Response<Boolean>> {
        emailState.value = Validators.email(emailState.value)
        passwordState.value = Validators.password(passwordState.value)
        confirmPasswordState.value = Validators.confirmPassword(
            confirmPasswordState.value,
            passwordState.value
        )
        nameState.value=Validators.personalDetails(nameState.value)
        return if (
            (!emailState.value.error) &&
            (!passwordState.value.error) &&
            (!confirmPasswordState.value.error) &&
            (!nameState.value.error)
        )
            repository.signUpWithEmailPassword(
                emailState.value.text,
                passwordState.value.text,
                nameState.value.text
            )
        else flow { emit(Response.Error(ValidationFailedException())) }
    }
}
