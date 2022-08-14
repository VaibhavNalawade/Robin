package com.vaibhav.robin.presentation.cart

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.entities.remote.CartItems
import com.vaibhav.robin.entities.ui.model.Product
import com.vaibhav.robin.presentation.product.showSnackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val _cartItemsUiState = MutableStateFlow<CartItemsUiState>(CartItemsUiState.Loading())
    val cartItemsUiState = _cartItemsUiState

    private val _wishlistItems = MutableStateFlow(emptyList<Product>())
    val wishlistItems = _wishlistItems

    private val _total = MutableStateFlow(0.0)
    val total = _total

    private val _subTotal = MutableStateFlow(0.0)
    val subTotal = _subTotal

    private val _shipping = MutableStateFlow(0.0)
    val shipping = _shipping

    private val _tax = MutableStateFlow(0.0)
    val tax = _tax

    private val _currency = MutableStateFlow("$")
    val currency = _currency

    private val _removeItemState = MutableStateFlow<RemoveItemsState>(RemoveItemsState.Init)
    val removeItemsState = _removeItemState

    init {
        viewModelScope.launch(Dispatchers.IO) {
        }
    }

     fun removeItem(cartItems: CartItems, state: SnackbarHostState) {
        viewModelScope.launch {
        var cancel =false
            val cartItemsUiState=(_cartItemsUiState.value as CartItemsUiState.Success).cartItems
            _cartItemsUiState.emit(CartItemsUiState.Loading())
        showSnackbar(
           message = "Product Removed From Cart",
           actionLabel = "Undo",
           snackbarHostState = state,
       dismissed = {},
       ) {
           cancel=true
       }
        delay(4000)
        if (!cancel)
        viewModelScope.launch(Dispatchers.IO) {
            removeItemsState.collect{
                when(it){
                    is RemoveItemsState.Error -> showSnackbar("Something Went Wrong With Remove item from cart", snackbarHostState = state)
                    is RemoveItemsState.Init -> TODO()
                    is RemoveItemsState.Success -> refreshCart()
                }
            }
        }
        else  _cartItemsUiState.value= CartItemsUiState.Success(cartItemsUiState)
    }
    }
    suspend fun refreshCart(){
        _cartItemsUiState.value= CartItemsUiState.Loading()
    }
}


sealed class CartItemsUiState {
    data class Success(val cartItems: List<CartItems>) : CartItemsUiState()
    data class Error(val exception: Exception) : CartItemsUiState()
    data class Loading(val progress: Int = -1) : CartItemsUiState()
}
sealed class RemoveItemsState {
    data class Success(val cartItems: CartItems) : RemoveItemsState()
    data class Error(val exception: Exception) : RemoveItemsState()
    object Init: RemoveItemsState()
}