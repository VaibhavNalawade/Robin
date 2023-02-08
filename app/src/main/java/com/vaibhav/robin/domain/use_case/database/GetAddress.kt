package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject


class GetAddress @Inject constructor(private val repository: DatabaseRepository) {
     suspend operator fun invoke()=repository.getAddress()
}