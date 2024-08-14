package com.vaibhav.robin.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.Firebase
import com.vaibhav.robin.domain.model.CurrentUserProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.model.Response.Success
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
    @Deprecated("Gone Removed in Feature use ui state from passthrough ui tree")
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
        email: String,
        password: String,
        name:String
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Loading)
            auth.createUserWithEmailAndPassword(email, password).await()
         /*   while (auth.currentUser==null){
                delay(1000)
            }*/
            updateProfile(name,null).collect{}
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    override suspend fun listenUserState() = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            if (auth.currentUser!=null)
            trySend(sendUserData())
            else trySend(sendGustUserData())
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun updateProfile(
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

    @Deprecated("Gone Removed in Feature use ui state from passthrough ui tree")
    override fun getProfileData(): CurrentUserProfileData? {
        try {
            auth.currentUser!!.apply {
                return CurrentUserProfileData(
                    true,
                    displayName?:"No Name",
                    photoUrl,
                    email?:"No Email",
                    isEmailVerified,
                    phoneNumber?:"No Phone",
                    uid
                )
            }
        } catch (e: Exception) {
            return null
        }
    }

    override fun getUserUid(): String?=auth.currentUser?.uid

    private fun sendUserData():CurrentUserProfileData{
        auth.currentUser!!.apply {
            return CurrentUserProfileData(
                true,
                displayName,
                photoUrl,
                email,
                isEmailVerified,
                phoneNumber,
                uid
            )
        }
    }
    private fun sendGustUserData()=CurrentUserProfileData()
}