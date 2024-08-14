package com.vaibhav.robin.presentation.ui.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.domain.exceptions.AuthenticationRequiredException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases, private val authUseCases: AuthUseCases
) : ViewModel() {

    var reviewsResponse by mutableStateOf<Response<List<Review>>>(Loading)
    var userReviewResponse by mutableStateOf<Response<Review>>(Loading)
    var favouriteToggleButtonState by mutableStateOf(false)

    fun loadCurrentUserReview(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        if (authUseCases.isUserAuthenticated()) {

            if (userReviewResponse is Loading) databaseUseCases.getUserReview(productId).collect {
                userReviewResponse = it
            }
        } else userReviewResponse = Error(AuthenticationRequiredException())
    }

    fun loadReview(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        if (reviewsResponse is Loading) databaseUseCases.getReview.invoke(productId).collect {
            reviewsResponse = it
        }
    }

    private val _selectedVariant = mutableStateOf<String?>(null)
    val selectedVariant = _selectedVariant

    private val _selectedSize = mutableStateOf<Int?>(0)
    val selectedSize = _selectedSize

    private val _stars = mutableStateOf(0)
    val stars = _stars


    fun checkFavorite(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        if(authUseCases.isUserAuthenticated())
        databaseUseCases.checkFavourite(productId).collect {
            when (it) {
                is Error -> Log.e(TAG, it.exception.message ?: it.exception.stackTraceToString())
                Loading -> {}
                is Success -> favouriteToggleButtonState = it.data
            }
        }
    }

    fun addFavorite(product: Product) = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.addFavourite(
            Favourite(
                product.id,
                selectedSize.value ?: 0,
                selectedVariant.value ?: product.variantIndex[0]
            )
        ).collect {
            when (it) {
                is Error -> Log.e(TAG, it.exception.message ?: it.exception.stackTraceToString())
                Loading -> {}
                is Success -> favouriteToggleButtonState = it.data
            }
        }
    }

    fun removeFavorite(productId: String) = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.removeFavourite(productId = productId).collect {
            when (it) {
                is Error -> Log.e(TAG, it.exception.message ?: it.exception.stackTraceToString())
                Loading -> {}
                is Success -> favouriteToggleButtonState = !it.data
            }
        }
    }

    var cartAdd by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun addCartItem(product: Product) =
        viewModelScope.launch(Dispatchers.IO) {
            databaseUseCases.addCartItem(
                CartItem(
                    cartId = UUID.randomUUID().toString(),
                    productId = product.id,
                    productName = product.name,
                    productVariant = selectedVariant.value ?: product.variantIndex[0],
                    productSize = selectedSize.value ?: 0,
                    productImage = product.media[selectedVariant.value
                        ?: product.variantIndex[0]]?.get(0),
                    price = product.sizes[selectedVariant.value ?: product.variantIndex[0]]?.get(
                        selectedSize.value ?: 0
                    )?.get("price") as Double,
                    brandLogo = product.brandLogo,
                    brandName = product.brandName
                )
            ).collect {
                Log.e(TAG,"Wtf")
                cartAdd = it
                when(it){
                    is Error ->  Log.e(TAG,it.exception.message?:it.exception.stackTraceToString())
                    Loading ->  Log.e(TAG,"Load")
                    is Success ->  Log.e(TAG,"Wtf")
                }
            }
        }

    fun loadProductDetails(productId: String) {
        loadCurrentUserReview(productId)
        loadReview(productId)
        checkFavorite(productId)
    }

    companion object {
        private const val TAG = "PRODUCT_VIEW_MODEL"
    }
}


