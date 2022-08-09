package com.vaibhav.robin.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.repository.AuthRepositoryImpl
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    fun provideUseCases(
        repo: AuthRepository
    ) = UseCases(
        isUserAuthenticated = IsUserAuthenticated(repo),
        signInWithEmailPassword = SignInWithEmailPassword(repo),
        signOut = SignOut(repo),
        getAuthState  = GetAuthState(repo),
        signUpWithEmailPassword = SignUpWithEmailPassword(repo)
    )
}
