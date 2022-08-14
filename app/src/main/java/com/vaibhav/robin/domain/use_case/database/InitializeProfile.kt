package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class InitializeProfile @Inject constructor(
    private val databaseRepository: FirestoreDatabaseRepository
) {
    suspend operator fun invoke() = databaseRepository.initializeProfile()
}