package com.vaibhav.robin.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override fun isUserAuthenticated() = auth.currentUser != null

    override suspend fun signInWithEmailPassword(email: String, password: String) = flow {
        try {
            emit(Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun signOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            Firebase.auth.signOut()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun signUpWithEmailPassword(
        email: String, password: String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override fun getAuthState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun UpdateProfile(
        displayName: String?, photoUri: String?
    ): Flow<Response<Boolean>> = flow {
        emit(Loading)
        val profileUpdates = userProfileChangeRequest {
            displayName?.let { this.displayName = displayName }
            photoUri?.let { this.photoUri = Uri.parse(photoUri) }
        }
        try {
            auth.currentUser?.updateProfile(profileUpdates)?.await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun passwordReset(
        email: String,
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.sendPasswordResetEmail(email).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun sendVerificationMail(): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.currentUser!!.sendEmailVerification()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override fun getProfileData(): ProfileData? {
        try {
            auth.currentUser!!.apply {
                return ProfileData(
                    displayName,
                    photoUrl,
                    email,
                    isEmailVerified,
                    phoneNumber,
                )
            }
        } catch (e: Exception) {
            return null
        }
    }

    override fun getUserUid(): String?=auth.currentUser?.uid
}