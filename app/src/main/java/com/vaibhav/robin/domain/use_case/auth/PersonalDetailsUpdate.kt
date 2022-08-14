package com.vaibhav.robin.domain.use_case.auth

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.flow

class PersonalDetailsUpdate @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(firstName: MutableState<TextFieldState>, lastname: MutableState<TextFieldState>): Flow<Response<Boolean>> {
        firstName.value=Validators.personalDetails(firstName.value)
        lastname.value=Validators.personalDetails(lastname.value)
        if (!firstName.value.error && !lastname.value.error)
        return repository.firebaseProfileChange(firstName.value.text+" "+ lastname.value.text,null)
       return flow { emit(Response.Error("Predate Failed")) }
    }
}