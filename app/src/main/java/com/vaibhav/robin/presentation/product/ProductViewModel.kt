package com.vaibhav.robin.presentation.product

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.vaibhav.robin.entities.remote.CartItems
import com.vaibhav.robin.entities.ui.model.Product
import com.vaibhav.robin.data.repository.ProductRepository
import com.vaibhav.robin.data.repository.UserRepository
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.navigation.userExist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _productId = MutableStateFlow(String())

    fun setProductId(str: String) {
        if (_productId.value != str)
            _productId.value = str
    }
    private val _selectedType = mutableStateOf(0)
    val selectedType=_selectedType


    private val selectedSize = mutableStateOf("")

    private val _productUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading())
    val productUiState = _productUiState

    private val _addCartItemUiState =
        MutableStateFlow<AddCartItemUiState>(AddCartItemUiState.Loading)
    val addCartItemUiState = _addCartItemUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _productId.collect {
                if (it.isNotEmpty())
                    ProductRepository().fetchProduct(it, _productUiState)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (userExist())
                UserRepository().checkCartItemExist(_productId.value, _addCartItemUiState)
            else _addCartItemUiState.value = AddCartItemUiState.Ready
        }
    }

     private fun launchAddCartItemWorker() {
        val product = (productUiState.value as ProductUiState.Success).product
        viewModelScope.launch(Dispatchers.IO) {
            UserRepository().addToCart(
                _addCartItemUiState,
                CartItems(
                    _productId.value,
                    product.name,
                    product.type[selectedType.value].media.images[0],
                    product.type[selectedType.value].price,
                    selectedType.value,
                    selectedSize.value
                )
            )
        }
    }

    fun addProductToCart(navController: NavHostController) {
        if (userExist()) {
            _addCartItemUiState.value = AddCartItemUiState.Loading
            launchAddCartItemWorker()
        } else {
            navController.navigate(RobinDestinations.LOGIN)
        }
    }

    private fun throwNullPointerException(): Nothing = run { throw NullPointerException() }
    private fun checkCartItem() = _addCartItemUiState.value !is AddCartItemUiState.Success
}


sealed class AddCartItemUiState {
    object Success : AddCartItemUiState()
    data class Error(val E: Exception) : AddCartItemUiState()
    object Loading : AddCartItemUiState()
    object AlreadyExits : AddCartItemUiState()
    object Ready : AddCartItemUiState()
}


sealed class ProductUiState {
    data class Success(val product: Product) : ProductUiState()
    data class Loading(val boolean: Boolean = true) : ProductUiState()
    data class Error(val exception: Exception) : ProductUiState()
}

