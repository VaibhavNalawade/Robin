package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class GetUserReview @Inject constructor(private val repo:DatabaseRepository) {
    suspend operator fun invoke(productId:String)=repo.getUserReview(productId)
}
