package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class GetProduct@Inject constructor(private val repo:FirestoreDatabaseRepository) {
    suspend operator fun invoke(productId:String)=repo.getProduct(productId)
}