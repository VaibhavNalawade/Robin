package com.vaibhav.unit

import com.vaibhav.robin.auth.Auth
import com.vaibhav.robin.auth.AuthState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AuthUnitTest {
    @Test
    fun createUser(): Unit = runBlocking {
        val a = MutableStateFlow<AuthState>(AuthState.Init)
        Auth.createUser("vaibhav@robbin.com", "123456", MutableStateFlow(AuthState.Init))
        runBlocking{
            a.collect {
                when (a.value) {
                    is AuthState.Successful -> assert(true)
                    else -> {
                        delay(5000)
                    }
                }
            }
        }
    }
}
