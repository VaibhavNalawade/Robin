package com.vaibhav.robin.presentation.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val authUseCases: AuthUseCases,
    val databaseUseCases: DatabaseUseCases
) : ViewModel() {
   var userAuthenticated by mutableStateOf(authUseCases.isUserAuthenticated())
        private set

    var profileData by mutableStateOf(authUseCases.getProfileData())
        private set

    fun signOut() = viewModelScope.launch {
        authUseCases.signOut().collect{
            Log.e("MainViewmodel","So")
        }
    }

    init {
        viewModelScope.launch {
            authUseCases.getAuthState().collect {
                userAuthenticated = it
                profileData = authUseCases.getProfileData()
            }
        }
    }
    var products by mutableStateOf<Response<List<Product>>>(Response.Loading)

    init {
        viewModelScope.launch {
            databaseUseCases.getProducts().collect{
                products = it
            }
        }
    }
}