package com.vaibhav.robin.data.repository

import com.vaibhav.robin.entities.remote.CartItems
import com.vaibhav.robin.data.remote.realtime.RealtimeDatabase
import com.vaibhav.robin.presentation.cart.CartItemsUiState
import com.vaibhav.robin.presentation.cart.RemoveItemsState
import com.vaibhav.robin.presentation.product.AddCartItemUiState
import kotlinx.coroutines.flow.MutableStateFlow

class UserRepository {
    suspend fun addToCart(
        addCartItemUiState: MutableStateFlow<AddCartItemUiState>,
        cartItems: CartItems
    ) {
        RealtimeDatabase().addCartItem(addCartItemUiState, cartItems)
    }
    suspend fun fetchCartItems(cartItemsUiState: MutableStateFlow<CartItemsUiState>) {
        RealtimeDatabase().readAllCartItems(cartItemsUiState)
    }

    suspend fun checkCartItemExist(
        productId: String,
        cartItemUiState: MutableStateFlow<AddCartItemUiState>
    ){
        RealtimeDatabase().checkCartItemsExits(productId,cartItemUiState)
    }
    suspend fun removeCartItem(cartItems: CartItems, removeItemsState: MutableStateFlow<RemoveItemsState>){
        RealtimeDatabase().removeCartItem(cartItems,removeItemsState)
    }
}