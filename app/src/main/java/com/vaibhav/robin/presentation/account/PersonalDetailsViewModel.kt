package com.vaibhav.robin.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.entities.ui.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalDetailsViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    ViewModel() {
    private val _firstName = mutableStateOf(TextFieldState())
    val firstName get() = _firstName

    private val _lastName = mutableStateOf(TextFieldState())
    val lastName get() = _lastName

    var response by mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set

    fun updateDetails() = viewModelScope.launch(Dispatchers.IO) {
        authUseCases.personalDetailsUpdate(firstName,lastName).collect {
            response=it
        }
    }

    fun retry() {
        response = mutableStateOf<Response<Boolean>>(Response.Success(false)).value
    }
}