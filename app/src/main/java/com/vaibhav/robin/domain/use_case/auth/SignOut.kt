package com.vaibhav.robin.domain.use_case.auth

import com.vaibhav.robin.domain.repository.AuthRepository
import javax.inject.Inject

class SignOut @Inject constructor(
    private val repository: AuthRepository
    ) {
    suspend operator fun invoke() = repository.signOut()
}
