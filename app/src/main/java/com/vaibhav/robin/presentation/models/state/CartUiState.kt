package com.vaibhav.robin.presentation.models.state

import com.vaibhav.robin.data.models.CartItem

sealed class CartUiState() {
    object Loading: CartUiState()
    object EmptyCart:CartUiState()
    data class Error(val exception: Exception):CartUiState()
    data class Success(val cartItems:List<CartItem>):CartUiState()
}