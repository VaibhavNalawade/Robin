package com.vaibhav.robin.domain.use_case.auth

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticated,
    val signInWithEmailPassword: SignInWithEmailPassword,
    val signOut: SignOut,
    val getAuthState: GetAuthState,
    val signUpWithEmailPassword: SignUpWithEmailPassword,
    val personalDetailsUpdate: PersonalDetailsUpdate,
    val getProfileData:GetUserProfileData,
    val sendPasswordResetMail:SendPasswordResetMail
)