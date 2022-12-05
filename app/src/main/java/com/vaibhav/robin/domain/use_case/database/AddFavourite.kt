package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class AddFavourite @Inject constructor(private val repo: DatabaseRepository) {
    suspend operator fun invoke(productId: String, favourite: Favourite) =
        repo.setFavorite(productId, favourite)

}
