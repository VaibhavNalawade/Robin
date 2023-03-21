package com.vaibhav.robin.domain.use_case.auth

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendPasswordResetMail @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(emailState: MutableState<TextFieldState>): Flow<Response<Boolean>> {
        emailState.value = Validators.email(emailState.value)
       return if (!emailState.value.error)
             authRepository.passwordReset(emailState.value.text)
        else flow { emit(Response.Error(ValidationFailedException())) }
    }

}
