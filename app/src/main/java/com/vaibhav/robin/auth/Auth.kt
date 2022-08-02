package com.vaibhav.robin.auth

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

object Auth {
    private const val TAG = "FirebaseAuth"
    suspend fun signInWithEmail(
        email: String,
        password: String,
        authState: MutableStateFlow<AuthState>
    ) {
        try {
            authState.emit(
                AuthState.Successful(
                    Firebase.auth.signInWithEmailAndPassword(
                        email,
                        password
                    ).await()
                )
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.message ?: exception.stackTraceToString())
            authState.emit(AuthState.Error(exception))
        }
    }
    suspend fun createUser(
        email: String,
        password: String,
        authState: MutableStateFlow<AuthState>
    ){
        try {
            authState.emit(
                AuthState.Successful(
                    Firebase.auth.createUserWithEmailAndPassword(
                        email,
                        password
                    ).await()
                )
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.message ?: exception.stackTraceToString())
            authState.emit(AuthState.Error(exception))
        }
    }
}
