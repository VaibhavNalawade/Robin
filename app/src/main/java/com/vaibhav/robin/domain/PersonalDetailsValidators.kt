package com.vaibhav.robin.domain

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.entities.ui.state.TextFieldState

class PersonalDetailsValidators(private val postValidation: () -> Unit) {

    fun validate(
        firstName: MutableState<TextFieldState>,
        lastName: MutableState<TextFieldState>
    ) {
        firstName.value = Validators.personalDetails(firstName.value)
        lastName.value = Validators.personalDetails(lastName.value)

        if (!firstName.value.error && !lastName.value.error) postValidation.invoke()
    }
}