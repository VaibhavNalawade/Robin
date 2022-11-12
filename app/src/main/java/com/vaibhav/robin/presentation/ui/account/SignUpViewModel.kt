package com.vaibhav.robin.presentation.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private val _signUpEmail = mutableStateOf(TextFieldState())
    val signUpEmail get() = _signUpEmail

    private val _signUpPassword = mutableStateOf(TextFieldState())
    val signUpPassword get() = _signUpPassword

    private val _signUpConfirmPassword = mutableStateOf(TextFieldState())
    val signUpConfirmPassword get() = _signUpConfirmPassword

    private val _signUpResponse = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpResponse get() = _signUpResponse


    fun signUp() = viewModelScope.launch(Dispatchers.IO) {
        authUseCases.signUpWithEmailPassword.invoke(
            _signUpEmail,
            _signUpPassword,
            _signUpConfirmPassword
        ).collect { response ->
            signUpResponse.value = response
        }
    }

    fun retry() {
        _signUpResponse.value = Response.Success(false)
    }

/*    private fun createProfile() = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.initializeProfile().collect {
            initializeProfileResponse=it
            when(it){
                is Response.Error -> {
                    if(authUseCases.isUserAuthenticated())
                        Firebase.auth.apply {
                            currentUser?.delete()
                            signOut()
                        }
                    responseMain=it
                }
                Response.Loading -> {}
                is Response.Success -> responseMain=it
            }
        }
    }*/
}

