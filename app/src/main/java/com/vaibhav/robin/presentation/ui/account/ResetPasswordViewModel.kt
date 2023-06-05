package com.vaibhav.robin.presentation.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val authUseCases: AuthUseCases) :
    ViewModel() {
    private val _email = mutableStateOf(TextFieldState())
    val email get() = _email
    var response by mutableStateOf<Response<Boolean>>(Response.Success(false))
    fun sendPasswordResetMail() = viewModelScope.launch(Dispatchers.IO) {
        authUseCases.sendPasswordResetMail(_email).collect{
            response = it
        }
    }
}


