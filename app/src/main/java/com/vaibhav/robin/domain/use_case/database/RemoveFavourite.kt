package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class RemoveFavourite @Inject constructor(private val repo: DatabaseRepository) {
    suspend operator fun invoke(productId: String) = repo.removeFavorite(productId)


}
