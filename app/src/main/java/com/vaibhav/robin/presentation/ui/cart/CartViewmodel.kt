package com.vaibhav.robin.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    fun getAuthState() = authUseCases.isUserAuthenticated()
    fun removeCartItem(cartId: String) = viewModelScope.launch {

        databaseUseCases.removeCartItems(cartId).collect {
            Log.e("Error", "it.message.message!!")
            when (it) {
                is Response.Error -> {
                    Log.e("Error", it.exception.message!!)
                }

                Loading -> Log.e("Loading", "loda")
                is Response.Success -> Log.e("Success", it.data.toString())
            }
        }
    }

}
