package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class GetCartItem@Inject constructor(private val repo:FirestoreDatabaseRepository) {
suspend operator fun invoke()=repo.getCartItems()
}
