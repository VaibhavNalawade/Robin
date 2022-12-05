package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class InitializeProfile @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend operator fun invoke() = databaseRepository.initializeProfile()
}