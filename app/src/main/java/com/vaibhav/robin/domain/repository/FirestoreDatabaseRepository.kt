package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface FirestoreDatabaseRepository {
    suspend fun updateProfile(hashMap: HashMap<String, Any>): Flow<Response<Boolean>>
    suspend fun initializeProfile():Flow<Response<Boolean>>
}