package com.vaibhav.robin.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.QueryProduct
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.FilterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val authUseCases: AuthUseCases,
    val databaseUseCases: DatabaseUseCases
) : ViewModel() {
    var userAuthenticated by mutableStateOf(authUseCases.isUserAuthenticated())
        private set
    var profileData by mutableStateOf(authUseCases.getProfileData())
        private set
    var categories by mutableStateOf<Response<List<MainCategory>>>(Response.Loading)
        private set
    var brands by mutableStateOf<Response<List<MainBrand>>>(Response.Loading)
        private set
    var cartItem by mutableStateOf<Response<List<CartItem>>>(Response.Loading)
        private set
    val filterState by mutableStateOf(FilterState())

    var selectedProduct by mutableStateOf<Product?>(null)
    var orders by mutableStateOf<Response<List<OrderItem>>>(Response.Loading)
        private set

    fun signOut() = viewModelScope.launch {
        authUseCases.signOut().collect {

        }
    }

    init {
        viewModelScope.launch {
            authUseCases.getAuthState().collect {
                userAuthenticated = it
                profileData = authUseCases.getProfileData()
                subscribeCartItems()
            }
        }
        viewModelScope.launch {
            databaseUseCases.getCategory().collect {
                when (it) {
                    is Response.Error -> {}
                    Response.Loading -> {}
                    is Response.Success -> {
                        categories = it
                    }
                }
            }
            databaseUseCases.getBrands().collect {
                when (it) {
                    is Response.Error -> {}
                    Response.Loading -> {}
                    is Response.Success -> {
                        brands = it
                    }
                }
            }
        }
        subscribeCartItems()
        subscribeOrders()
    }

    var products by mutableStateOf<Response<List<Product>>>(Response.Loading)

    fun fetchUiState() =
        viewModelScope.launch(Dispatchers.IO) {
            databaseUseCases.filterProducts(queryProduct = QueryProduct(null, null, null)).collect {
                products = it
            }
        }

    fun quarry(queryProduct: QueryProduct) = viewModelScope.launch {
        databaseUseCases.filterProducts(queryProduct = queryProduct).collect {
            products = it
        }
    }

    private var cartItemsJob: Job? = null
    private fun subscribeCartItems() {
        cartItemsJob?.cancel()
        cartItemsJob = viewModelScope.launch {
            databaseUseCases.listenForCartItems()
                .catch { Log.e("AT", it.message ?: it.stackTraceToString()) }
                .collect {
                    cartItem = it
                }
        }
    }

    private var ordersJob: Job? = null
    private fun subscribeOrders() {
        ordersJob?.cancel()
        ordersJob = viewModelScope.launch {
            databaseUseCases.listenOrder()
                .cancellable()
                .catch { Log.e("AT", it.message ?: it.stackTraceToString()) }
                .collect {
                    orders = it
                }
        }
    }
}