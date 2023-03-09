package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticated(): Boolean
    suspend fun signInWithEmailPassword(email: String, password: String): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun signUpWithEmailPassword(email: String, password: String, name:String): Flow<Response<Boolean>>
    fun getAuthState(): Flow<Boolean>
    suspend fun passwordReset(email: String): Flow<Response<Boolean>>
    suspend fun sendVerificationMail(): Flow<Response<Boolean>>
    suspend fun UpdateProfile(displayName: String?, photoUri: String?): Flow<Response<Boolean>>
    fun getProfileData(): ProfileData?
    fun getUserUid():String?
}