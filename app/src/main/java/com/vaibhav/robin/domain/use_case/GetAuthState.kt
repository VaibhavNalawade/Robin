package com.vaibhav.robin.domain.use_case

import com.vaibhav.robin.domain.repository.AuthRepository
import javax.inject.Inject

class GetAuthState @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getFirebaseAuthState()
}