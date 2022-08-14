package com.vaibhav.unit

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.repository.AuthRepositoryImpl
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.use_case.auth.*
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class AuthUseCasesTest @Inject constructor() {

    private val repo = AuthRepositoryImpl(Firebase.auth)
    private val useCases = AuthUseCases(
        isUserAuthenticated = IsUserAuthenticated(repo),
        signInWithEmailPassword = SignInWithEmailPassword(repo),
        signOut = SignOut(repo),
        getAuthState = GetAuthState(repo),
        signUpWithEmailPassword = SignUpWithEmailPassword(repo),
        personalDetailsUpdate = PersonalDetailsUpdate(repo)
    )
    private val rightEmail = mutableStateOf(TextFieldState(text = email()))
    private val wrongEmail = mutableStateOf(TextFieldState(text = "vn14989gmail.com"))
    private val rightPassword = mutableStateOf(TextFieldState(text = "123456"))
    private val wrongPassword = mutableStateOf(TextFieldState(text = "12345"))
    private val rightName = mutableStateOf(TextFieldState(text = "ABED"))
    private val wrongName = mutableStateOf(TextFieldState(text = ""))
    private fun email() = "vn${(0..100000).random()}@gmail.com"

    @Test
    fun `Use Case signup right Test`() = runTest {
        useCases.signUpWithEmailPassword(rightEmail, rightPassword,rightPassword).collect {
            when (it) {
                is Success->assert(true)
                is Error -> throw Exception(it.message)
                is Loading -> {}
            }
        }
    }
    @Test
    fun `Use Case signup wrong confirm password Test`() = runTest {
        useCases.signUpWithEmailPassword(rightEmail, rightPassword,wrongPassword).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }

    @Test
    fun `Use Case signup wrong email Test`() = runTest {
        useCases.signUpWithEmailPassword(wrongEmail, rightPassword,rightPassword).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }
    @Test
    fun `Use Case signup wrong password Test`() = runTest {
        useCases.signUpWithEmailPassword(wrongEmail, rightPassword,rightPassword).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }

    @Test
    fun `Use Case signIn right Test`() = runTest {
        useCases.signInWithEmailPassword(rightEmail, rightPassword).collect {
            when (it) {
                is Success->assert(true)
                is Error -> assert(it.message != "Predate Failed")
                is Loading -> {}
            }
        }
    }


    @Test
    fun `Use Case signIn wrong email Test`() = runTest {
        useCases.signInWithEmailPassword(wrongEmail, rightPassword).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }
    @Test
    fun `Use Case sign in wrong password Test`() = runTest {
        useCases.signInWithEmailPassword(wrongEmail, wrongPassword).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }

    @Test
    fun `Use Case Personal details right Test`() = runTest {
        useCases.personalDetailsUpdate(rightName, rightName).collect {
            when (it) {
                is Success->assert(true)
                is Error -> assert(it.message != "Predate Failed")
                is Loading -> {}
            }
        }
    }


    @Test
    fun `Use Case personal details wrong firstname Test`() = runTest {
        useCases.signInWithEmailPassword(wrongName, rightName).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }
    @Test
    fun `Use Case personal details wrong lastname Test`() = runTest {
        useCases.signInWithEmailPassword(rightName, wrongName).collect {
            when (it) {
                is Success->assert(false)
                is Error -> assert(it.message == "Predate Failed")
                is Loading -> {}
            }
        }
    }

}