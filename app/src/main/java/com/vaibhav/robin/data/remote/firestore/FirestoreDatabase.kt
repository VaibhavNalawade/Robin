package com.vaibhav.robin.data.remote.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.entities.ui.model.Product
import com.vaibhav.robin.data.repository.RobinDataSource
import com.vaibhav.robin.presentation.product.ProductUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class FirestoreDatabase : RobinDataSource {
    suspend fun fetchProduct(id: String, product: MutableStateFlow<ProductUiState>)  {
        try {
            val snapshot = Firebase.firestore.collection("Product").document(id).get().await()
            product.emit(ProductUiState.Success(snapshot.toObject<Product>()!!))
        } catch (exception: Exception) {
            product.emit(ProductUiState.Error(exception))
        }
    }

}