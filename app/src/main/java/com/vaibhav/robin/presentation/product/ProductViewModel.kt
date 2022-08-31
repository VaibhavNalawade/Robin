package com.vaibhav.robin.presentation.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _productId = MutableStateFlow(String())

    var productResponse by mutableStateOf<Response<Product>>(Response.Loading)

    fun setProductId(str: String) {
        if (_productId.value != str && (productResponse as? Success)?.data?.name.isNullOrBlank())
            viewModelScope.launch(Dispatchers.IO) {
                databaseUseCases.getProduct("D3MjFbzN2Grpl0QRU9cv").collect {
                    productResponse = it
                }
            }
    }

    private val _selectedVariant = mutableStateOf(0)
    val selectedVariant = _selectedVariant

    private val _selectedSize = mutableStateOf(0)
    val selectedSize = _selectedSize

    /*
     private val _productUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading())
     val productUiState = _productUiState

     private val _addCartItemUiState =
         MutableStateFlow<AddCartItemUiState>(AddCartItemUiState.Loading)
     val addCartItemUiState = _addCartItemUiState

     init {



         fun launchAddCartItemWorker() {
             val product = (productUiState.value as ProductUiState.Success).product
             viewModelScope.launch(Dispatchers.IO) {
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

     }*/
}


