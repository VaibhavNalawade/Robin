package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class RemoveAddress@Inject constructor(private val repo:DatabaseRepository) {
suspend operator fun invoke(id:String)=repo.removeAddress(id)
}
