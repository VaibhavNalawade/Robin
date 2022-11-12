package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class GetReview @Inject constructor(private val repo:FirestoreDatabaseRepository){
        suspend operator fun invoke(productId:String)=repo.getReviews(productId)
        }