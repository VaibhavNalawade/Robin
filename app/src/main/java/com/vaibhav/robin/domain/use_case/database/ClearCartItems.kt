package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class ClearCartItems@Inject constructor(private val repo : DatabaseRepository) {
    suspend operator fun invoke()=repo.clearCartItems()
}