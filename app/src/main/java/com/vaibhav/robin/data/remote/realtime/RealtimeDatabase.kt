package com.vaibhav.robin.data.remote.realtime

import android.util.Log
import androidx.compose.runtime.MutableState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.entities.remote.BannerImage
import com.vaibhav.robin.entities.remote.CartItems
import com.vaibhav.robin.entities.remote.TrendingChipData
import com.vaibhav.robin.ui.cart.CartItemsUiState
import com.vaibhav.robin.ui.cart.RemoveItemsState
import com.vaibhav.robin.ui.common.TrendingChipState
import com.vaibhav.robin.ui.product.AddCartItemUiState
import com.vaibhav.robin.userExist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class RealtimeDatabase constructor(flag:Int=-1) {
    companion object {
        const val TAG = "RealtimeData"
    }
    private val uid = if (userExist())Firebase.auth.currentUser!!.uid else ""

    fun fetchBannerData(_bannerImage: MutableState<List<BannerImage>>) =
        Firebase.database.reference.child("bannerData").get().addOnSuccessListener {
            _bannerImage.value = it.getValue<List<BannerImage>>()!!
        }.addOnFailureListener {
            Log.e(TAG, it.message ?: it.stackTraceToString())
        }

    suspend fun addCartItem(cart: MutableStateFlow<AddCartItemUiState>, cartItems: CartItems) {
        try {
            val ref = uid.let { Firebase.database.reference.child("Cart").child(it) }
            ref.child(cartItems.productId).setValue(cartItems).await()
            cart.emit(AddCartItemUiState.Success)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: e.stackTraceToString())
            cart.emit(AddCartItemUiState.Error(e))
        }
    }


    suspend fun readAllCartItems(cartItemsUiState: MutableStateFlow<CartItemsUiState>) {
        try {
            val snapshot = Firebase.database.reference.child("Cart")
                .child(uid).get().await()
            val list = mutableListOf<CartItems>()
            snapshot.children.forEach {
                list.add(it.getValue<CartItems>()!!)
            }
            cartItemsUiState.emit(CartItemsUiState.Success(list))
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: e.stackTraceToString())
            cartItemsUiState.emit( CartItemsUiState.Error(e))
        }
    }

        suspend fun checkCartItemsExits(productID: String, cartItemUiState: MutableStateFlow<AddCartItemUiState>)  {
            try {
                val snapshot =Firebase.database.reference.child("Cart")
                    .child(uid).child(productID).get().await()
                if (snapshot.exists())
                    cartItemUiState.value=AddCartItemUiState.AlreadyExits
                else cartItemUiState.value= AddCartItemUiState.Ready
            } catch (exception:Exception){
                Log.e(TAG, exception.message ?: exception.stackTraceToString())
                cartItemUiState.value= AddCartItemUiState.Error(exception)
            }
        }
    suspend fun removeCartItem(
        cartItems: CartItems,
        removeItemsState: MutableStateFlow<RemoveItemsState>
    ) {
       return try {
            Firebase.database.reference.child("Cart").child(uid).child(cartItems.productId).removeValue().await()
           removeItemsState.value=RemoveItemsState.Success(cartItems)
        }
        catch (exception:Exception){
            Log.e(TAG, exception.message ?: exception.stackTraceToString())
            removeItemsState.emit(RemoveItemsState.Error(exception))
        }
    }

    suspend fun readTrendingItem(trendingItemsState: MutableStateFlow<TrendingChipState>) {
        return try {
            val snapshot = Firebase.database.reference.child("TrendingTopics").get().await()
            trendingItemsState.emit(TrendingChipState.Complete(snapshot.getValue<List<TrendingChipData>>()!!))
        } catch (exception: Exception) {
            Log.e(TAG, exception.message ?: exception.stackTraceToString())
            trendingItemsState.emit(TrendingChipState.Error(exception))
        }
    }
}