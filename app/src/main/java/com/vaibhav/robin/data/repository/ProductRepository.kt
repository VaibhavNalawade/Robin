package com.vaibhav.robin.data.repository

import com.vaibhav.robin.data.remote.firestore.FirestoreDatabase
import com.vaibhav.robin.data.remote.realtime.RealtimeDatabase
import com.vaibhav.robin.presentation.common.TrendingChipState
import com.vaibhav.robin.presentation.product.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow

class ProductRepository {
    suspend fun fetchProduct(id: String, product: MutableStateFlow<ProductUiState>) {
        FirestoreDatabase().fetchProduct(id,product)
    }
    suspend fun fetchTrendingItems(trendingItemsState: MutableStateFlow<TrendingChipState>) {
        RealtimeDatabase().readTrendingItem(trendingItemsState)
    }
}