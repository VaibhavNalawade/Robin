package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.QueryProduct
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
    suspend fun setFavorite(favourite: Favourite):Flow<Response<Boolean>>
    suspend fun removeFavorite(productId: String):Flow<Response<Boolean>>
    suspend fun checkProductFavorite(productId: String):Flow<Response<Boolean>>
    suspend fun addCartItem(cartItem: CartItem):Flow<Response<Boolean>>
    suspend fun getCartItems():Flow<Response<List<CartItem>>>
    suspend fun getProducts():Flow<Response<List<Product>>>
    suspend fun listenForCartItems():Flow<Response<List<CartItem>>>
    suspend fun deleteCartItem(productId: String):Flow<Response<Boolean>>
    suspend fun getCategory():Flow<Response<List<MainCategory>>>
    suspend fun getBrand():Flow<Response<List<MainBrand>>>
    suspend fun queryProduct(queryProduct: QueryProduct):Flow<Response<List<Product>>>
    suspend fun addAddress(address:Address):Flow<Response<Boolean>>
    suspend fun getAddress():Flow<Response<List<Address>>>
    suspend fun removeAddress(id: String):Flow<Response<Boolean>>
    suspend fun addPayment(payment:PaymentData):Flow<Response<Boolean>>
    suspend fun getPayments():Flow<Response<List<PaymentData>>>
    suspend fun removePayments(id:String):Flow<Response<Boolean>>
    suspend fun placeOrder(order:OrderItem):Flow<Response<Boolean>>
    suspend fun listenForOrders():Flow<Response<List<OrderItem>>>
    suspend fun clearCartItems():Flow<Response<Boolean>>
}