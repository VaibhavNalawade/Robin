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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCases: AuthUseCases) : ViewModel() {
    private val _email = mutableStateOf(TextFieldState())
    val email get() = _email

    private val _password = mutableStateOf(TextFieldState())
    val password get() = _password

    var signInResponse by mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set
    fun signWithEmailPassword() = viewModelScope.launch {
        authUseCases.signInWithEmailPassword(_email, _password).collect{ response->
            signInResponse = response
        }
    }

}