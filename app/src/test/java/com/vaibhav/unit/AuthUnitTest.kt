package com.vaibhav.unit

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.repository.AuthRepositoryImpl
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AuthUnitTest {
    @Test
    fun `create Account with email test`() = runTest {
        val authRepo = AuthRepositoryImpl(Firebase.auth)
        authRepo.firbaseSignUpWithEmailPassword(
            "vn${(0..Int.MAX_VALUE).random()}@gmail.com", "123456"
        ).collect {
            when (it) {
                is Response.Loading -> {}
                is Response.Error -> throw Exception(it.message)
                is Response.Success -> assert(authRepo.isUserAuthenticatedInFirebase())
            }
        }
    }

    @Test
    fun `sign in with email test`() = runTest {
        val authRepo = AuthRepositoryImpl(Firebase.auth)
        authRepo.firebaseSignInWithEmailPassword(
            "vn14989@gmail.com", "123456"
        ).collect {
            when (it) {
                is Response.Loading -> {}
                is Response.Error -> throw Exception(it.message)
                is Response.Success -> assert(authRepo.isUserAuthenticatedInFirebase())
            }
        }
    }

    @Test
    fun `sign out test`() = runTest {
        val authRepo = AuthRepositoryImpl(Firebase.auth)
        authRepo.firebaseSignInWithEmailPassword("vn14989@gmail.com", "123456")
        authRepo.signOut().collect {
            when (it) {
                is Response.Loading -> {}
                is Response.Error -> throw Exception(it.message)
                is Response.Success -> {
                    if (Firebase.auth.currentUser == null) assert(it.data)
                }
            }
        }
    }
}

