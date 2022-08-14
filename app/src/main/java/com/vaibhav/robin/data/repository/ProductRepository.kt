package com.vaibhav.robin.data.repository

import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.presentation.common.TrendingChipState
import com.vaibhav.robin.presentation.product.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow

class ProductRepository {
    suspend fun fetchProduct(id: String, product: MutableStateFlow<ProductUiState>) {
        FirestoreSource().fetchProduct(id, product)
    }

    suspend fun fetchTrendingItems(trendingItemsState: MutableStateFlow<TrendingChipState>) {

    }
}