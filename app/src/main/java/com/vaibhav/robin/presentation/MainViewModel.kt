package com.vaibhav.robin.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.FilterChipState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    val categories = mutableStateListOf<FilterChipState>()

    val brands = mutableStateListOf<FilterChipState>()


    fun signOut() = viewModelScope.launch {
        authUseCases.signOut().collect {

        }
    }

    init {
        viewModelScope.launch {
            authUseCases.getAuthState().collect {
                userAuthenticated = it
                profileData = authUseCases.getProfileData()
            }
        }
        viewModelScope.launch {
            databaseUseCases.getCategory().collect {
                when (it) {
                    is Response.Error ->{}
                    Response.Loading ->{}
                    is Response.Success -> {
                        categories.clear()
                        it.data.forEach {
                            categories.add(FilterChipState(it.name))
                        }
                    }
                }
            }
            databaseUseCases.getBrands().collect {
                when (it) {
                    is Response.Error -> {}
                    Response.Loading -> {}
                    is Response.Success -> {
                        brands.clear()
                        it.data.forEach {
                            brands.add(FilterChipState(it.name))
                        }
                    }
                }
            }
        }
    }

    var products by mutableStateOf<Response<List<Product>>>(Response.Loading)

    fun fetchUiState() =
        viewModelScope.launch(Dispatchers.IO) {
            databaseUseCases.getProducts().collect {
                products = it
            }
        }

    fun quarry() =viewModelScope.launch {
      //  databaseUseCases.
    }
}