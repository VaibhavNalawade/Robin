package com.vaibhav.robin.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.entities.remote.BannerImage
import com.vaibhav.robin.entities.ui.model.Product
import com.vaibhav.robin.data.repo.ProductRepository
import com.vaibhav.robin.data.unsplash.UnsplashApi
import com.vaibhav.robin.data.unsplash.model.Results
import com.vaibhav.robin.ui.common.TrendingChipState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _trendingProducts = mutableStateOf(emptyList<Results>())
    val trendingProducts = _trendingProducts

    private val _tradingProductRequest = mutableStateOf(emptyList<Results>())
    val tradingProductRequest = _tradingProductRequest

    private val _bannerImageData = mutableStateOf(emptyList<BannerImage>())
    val bannerImageData = _bannerImageData

    private val _productlist= MutableStateFlow(emptyList<Product>())
    val productlist= _productlist

    private val _trendingItemsState = MutableStateFlow<TrendingChipState>(TrendingChipState.Loading)
    val trendingItemsState=_trendingItemsState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _tradingProductRequest.value = UnsplashApi().getCollection("UIkSJwNi_e4", 30, 1)
            _trendingProducts.value = UnsplashApi().getCollection("TO7iP5kRiFA", 30, 1)
            ProductRepository().fetchTrendingItems(_trendingItemsState)
         //   Repository().getBannerData(_bannerImageData)
            //Repository(FirestoreDatabase()).fetchProduct(_productlist)
        }
    }
}
