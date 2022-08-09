package com.vaibhav.robin.presentation.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.PersonalDetailsValidators
import com.vaibhav.robin.entities.ui.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel


class PersonalDetailsViewModel:ViewModel() {
    private val _firstName = mutableStateOf(TextFieldState())
    val firstName get() = _firstName

    private val _lastName = mutableStateOf(TextFieldState())
    val lastName get() = _lastName

    fun predatePersonalDetails(postValidation:()-> Unit) {
        PersonalDetailsValidators(postValidation).validate(_firstName,_lastName)
    }
}