package com.vaibhav.robin.presentation.ui.product

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
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases, private val authUseCases: AuthUseCases
) : ViewModel() {

    private var _productId by mutableStateOf(String())

    var productResponse by mutableStateOf<Response<Product>>(Loading)
    var reviewsResponse by mutableStateOf<Response<List<Review>>>(Loading)
    var yourReviewResponse by mutableStateOf<Response<Review>>(Loading)
    var favouriteToggleButtonState by mutableStateOf(false)
    fun getAuthState() = authUseCases.isUserAuthenticated()

    fun setProductId(str: String) {
        if (_productId != str && (productResponse as? Success)?.data?.name.isNullOrBlank()) viewModelScope.launch(
            Dispatchers.IO
        ) {
            _productId = str
            databaseUseCases.getProduct(_productId).collect {
                productResponse = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (authUseCases.isUserAuthenticated()) {
                databaseUseCases.checkFavourite(str).collect {
                    when (it) {
                        is Error -> {}
                        Loading -> {}
                        is Success -> favouriteToggleButtonState = it.data
                    }
                }
            }
        }
    }

    fun loadCurrentUserReview() = viewModelScope.launch(Dispatchers.IO) {
        if (authUseCases.isUserAuthenticated()) {

            if (yourReviewResponse is Loading) databaseUseCases.getUserReview(_productId).collect {
                yourReviewResponse = it
            }
        } else yourReviewResponse = Error(AuthenticationRequiredException())
    }

    fun loadReview() = viewModelScope.launch(Dispatchers.IO) {
        if (reviewsResponse is Loading) databaseUseCases.getReview.invoke(_productId).collect {
            reviewsResponse = it
        }
    }

    private val _selectedVariant = mutableStateOf(0)
    val selectedVariant = _selectedVariant

    private val _selectedSize = mutableStateOf(0)
    val selectedSize = _selectedSize

    private val _stars = mutableStateOf(0)
    val stars = _stars

    fun addFavorite() = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.addFavourite(
            _productId, Favourite(_productId, selectedSize.value, selectedVariant.value)
        ).collect {
            when (it) {
                is Error -> {

                }

                Loading -> {

                }

                is Success -> favouriteToggleButtonState = it.data
            }
        }
    }

    fun removeFavorite() = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.removeFavourite(_productId).collect {
            when (it) {
                is Error -> {

                }

                Loading -> {

                }

                is Success -> favouriteToggleButtonState = !it.data
            }
        }
    }

    var cartAdd by mutableStateOf<Response<Boolean>>(Success(false))
        private set

    fun addCartItem() =
        viewModelScope.launch(Dispatchers.IO) {
            val item=productResponse as? Success
            if (item != null) {
                databaseUseCases.addCartItem(
                    CartItem(
                        productId= _productId,
                        productName = item.data.name,
                        productVariant = selectedVariant.value,
                        productSize = selectedSize.value,
                        productImage = item.data.variant[selectedVariant.value].media.images[0],
                        brand = item.data.brand,
                        price = item.data.variant[selectedVariant.value].size[selectedSize.value].price.retail
                    )
                ).collect {
                    cartAdd = it
                }
            }
        }
}


