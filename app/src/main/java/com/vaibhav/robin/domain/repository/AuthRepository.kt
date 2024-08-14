package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.domain.model.CurrentUserProfileData
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    @Deprecated("Gone Removed in Feature use ui state from passthrough ui tree")
    fun isUserAuthenticated(): Boolean
    suspend fun signInWithEmailPassword(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun signUpWithEmailPassword(email: String, password: String, name:String): Flow<Response<Boolean>>
    suspend fun listenUserState(): Flow<CurrentUserProfileData>
    suspend fun passwordReset(email: String): Flow<Response<Boolean>>
    suspend fun sendVerificationMail(): Flow<Response<Boolean>>
    suspend fun updateProfile(displayName: String?, photoUri: String?): Flow<Response<Boolean>>
    @Deprecated("Gone Removed in Feature use ui state from passthrough ui tree")
    fun getProfileData(): CurrentUserProfileData?
    fun getUserUid():String?
}