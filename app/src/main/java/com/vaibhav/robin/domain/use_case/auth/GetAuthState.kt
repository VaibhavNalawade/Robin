package com.vaibhav.robin.domain.use_case.auth

import com.vaibhav.robin.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthState @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getAuthState()
}