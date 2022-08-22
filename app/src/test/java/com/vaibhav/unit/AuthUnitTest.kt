package com.vaibhav.unit

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.repository.AuthRepositoryImpl
import com.vaibhav.robin.domain.model.Response.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AuthUnitTest {
    private val authRepo = AuthRepositoryImpl(Firebase.auth)
    private val testScope = TestScope()

    @Test
    fun `create Account with email test`() = runTest {
        authRepo.signUpWithEmailPassword(email(), password()).collect {
            when (it) {
                is Loading -> {}
                is Error -> throw Exception(it.message)
                is Success -> assert(authRepo.isUserAuthenticated())
            }
        }
    }

    @Test
    fun `sign out test`() = testScope.runTest {
        val email = email()
        val password = password()
        val job = testScope.launch {
            predateSignup(email, password)
            predateSignOut()
        }
        job.join()
        if (job.isCompleted)
            authRepo.signOut().collect {
                when (it) {
                    is Loading -> {}
                    is Error -> throw Exception(it.message)
                    is Success -> {
                        if (Firebase.auth.currentUser == null) assert(it.data)
                    }
                }
            }
    }

    @Test
    fun `sign in with email test`() = testScope.runTest {
        val email = email()
        val password = password()
        val job = testScope.launch { predateSignup(email, password) }
        job.join()
        if (job.isCompleted)
            testScope.launch {
                authRepo.signInWithEmailPassword(email, password).collect { response ->
                    when (response) {
                        is Loading -> {}
                        is Error -> throw Exception(response.message)
                        is Success -> assert(authRepo.isUserAuthenticated())
                    }
                }
            }
    }


    //Todo Need to Fix This one Fails to do job null pointer in current user
    @Test
    fun `change profile name and photo test`() = testScope.runTest {
        val email = email()
        val password = password()

        val job = testScope.launch { predateSignup(email, password) }
        job.join()
        if (job.isCompleted)
            testScope.launch {
                authRepo.UpdateProfile("tango mango", "http://wwe.cy/4.jpg")
                    .collect { response ->
                        when (response) {
                            is Loading -> {}
                            is Error -> {
                                throw (Exception(response.message))
                            }
                            is Success -> {
                                assert(true)
                            }
                        }
                    }
            }
    }


    @Test
    fun `send verification test`() = testScope.runTest {
        val email = email()
        val password = password()
        val job = testScope.launch { predateSignup(email, password) }
        job.join()
        if (job.isCompleted)
            testScope.launch {
                authRepo.sendVerificationMail().collect { response ->
                    when (response) {
                        is Loading -> {}
                        is Error -> throw Exception(response.message)
                        is Success -> {
                            assert(true)
                        }
                    }
                }
            }
    }

    @Test
    fun `reset password test`() = testScope.runTest {
        val email = email()
        val password = password()
        val job = testScope.launch {
            predateSignup(email, password)
        }
        job.join()
        if (job.isCompleted)
            testScope.launch {
                authRepo.passwordReset(email).collect { response ->
                    when (response) {
                        is Loading -> {}
                        is Error -> throw Exception(response.message)
                        is Success -> {
                            assert(true)
                        }
                    }
                }
            }
        else throw (Exception("UnknownError"))
    }

    suspend fun predateSignup(email: String, password: String) {
        authRepo.signUpWithEmailPassword(email, password).collect {
            if (it is Error) throw Exception(it.message)
        }
    }

    suspend fun predateSignOut() {
        authRepo.signOut().collect {
            if (it is Error) throw Exception(it.message)
        }
    }


    private fun email() = "vn${(0..100000).random()}@gmail.com"
    private fun password() = "123456"
}