package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class AddCartItem @Inject constructor(private val repo: FirestoreDatabaseRepository) {
    suspend operator fun invoke(productId: String, cartItem: CartItem) =
        repo.addCartItem(productId, cartItem)
}
