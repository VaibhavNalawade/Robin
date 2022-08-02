package com.vaibhav.robin.data.repo

import com.vaibhav.robin.data.remote.firestore.FirestoreDatabase
import com.vaibhav.robin.data.remote.realtime.RealtimeDatabase
import com.vaibhav.robin.ui.common.TrendingChipState
import com.vaibhav.robin.ui.product.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow

class ProductRepository {
    suspend fun fetchProduct(id: String, product: MutableStateFlow<ProductUiState>) {
        FirestoreDatabase().fetchProduct(id,product)
    }
    suspend fun fetchTrendingItems(trendingItemsState: MutableStateFlow<TrendingChipState>) {
        RealtimeDatabase().readTrendingItem(trendingItemsState)
    }
}