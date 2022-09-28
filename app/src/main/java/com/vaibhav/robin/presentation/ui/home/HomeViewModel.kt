package com.vaibhav.robin.presentation.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authUseCases: AuthUseCases, private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

/*    private val _trendingProducts = mutableStateOf(emptyList<Results>())
    val trendingProducts = _trendingProducts

    private val _tradingProductRequest = mutableStateOf(emptyList<Results>())
    val tradingProductRequest = _tradingProductRequest

    private val _bannerImageData = mutableStateOf(emptyList<BannerImage>())
    val bannerImageData = _bannerImageData

    private val _productlist = MutableStateFlow(emptyList<Product>())
    val productlist = _productlist*/

/*    private val _trendingItemsState = MutableStateFlow<TrendingChipState>(TrendingChipState.Loading)
    val trendingItemsState = _trendingItemsState*/

    var userAuthenticated by mutableStateOf(authUseCases.isUserAuthenticated())
        private set

    var profileData by mutableStateOf(authUseCases.getProfileData())
        private set

    init {
        viewModelScope.launch {
            authUseCases.getAuthState().collect {
                userAuthenticated = it
                if (true) profileData = authUseCases.getProfileData()
            }
        }
    }

    suspend fun signOut() = authUseCases.signOut()


}
