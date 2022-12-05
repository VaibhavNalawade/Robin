package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun updateProfile(hashMap: HashMap<String, Any>): Flow<Response<Boolean>>
    suspend fun initializeProfile():Flow<Response<Boolean>>
    suspend fun getProduct(productId:String):Flow<Response<Product>>

    suspend fun getReviews(productId:String):Flow<Response<List<Review>>>

    suspend fun writeReview(productId: String, review: Review):Flow<Response<Boolean>>

    suspend fun getUserReview(productId: String):Flow<Response<Review>>

    suspend fun setFavorite(productId: String,favourite: Favourite):Flow<Response<Boolean>>

    suspend fun removeFavorite(productId: String):Flow<Response<Boolean>>

    suspend fun checkProductFavorite(productId: String):Flow<Response<Boolean>>

    suspend fun addCartItem(cartItem: CartItem):Flow<Response<Boolean>>

    suspend fun getCartItems():Flow<Response<List<CartItem>>>

    suspend fun getProducts():Flow<Response<List<Product>>>
    suspend fun  listenForCartItems():Flow<Response<List<CartItem>>>
    suspend fun deleteCartItem(productId: String):Flow<Response<Boolean>>
}