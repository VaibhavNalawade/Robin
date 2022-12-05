package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddCartItem @Inject constructor(private val repo: DatabaseRepository) {
    suspend operator fun invoke( cartItem: CartItem) =
        repo.addCartItem( cartItem)
}
