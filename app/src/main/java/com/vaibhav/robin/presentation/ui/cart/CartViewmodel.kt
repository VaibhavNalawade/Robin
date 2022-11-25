package com.vaibhav.robin.presentation.ui.cart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {
    var cartItem by mutableStateOf<Response<List<CartItem>>>(Loading)
        private set

    //Note This only allow 100 cartItems
    val productData: SnapshotStateList<Response<Product>> =
        (1..100).map { Loading }.toMutableStateList()


    suspend fun launch() = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.getCartItem().collect {
            cartItem = it
        }
    }

    suspend fun getDetails(productID: String, index: Int) = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.getProduct(productID).collect {
            productData.add(index, it)
        }
    }


    fun getAuthState() = authUseCases.isUserAuthenticated()
}
/*}
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
}*/
