package com.vaibhav.robin.domain.repository

import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInWithEmailPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    suspend fun firebaseSignUpWithEmailPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>

    suspend fun firebasePasswordReset(email: String): Flow<Response<Boolean>>

    suspend fun firebaseSendVerificationMail(): Flow<Response<Boolean>>

    suspend fun firebaseProfileChange(
        displayName: String?,
        photoUri: String?
    ): Flow<Response<Boolean>>
}