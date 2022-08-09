package com.vaibhav.robin.domain.use_case

data class UseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val signInWithEmailPassword: SignInWithEmailPassword,
    val signOut: SignOut,
    val getAuthState: GetAuthState,
    val signUpWithEmailPassword: SignUpWithEmailPassword
)