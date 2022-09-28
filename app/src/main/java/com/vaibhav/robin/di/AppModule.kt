package com.vaibhav.robin.di


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.data.repository.AuthRepositoryImpl
import com.vaibhav.robin.data.repository.FirestoreRepositoryImpl
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import com.vaibhav.robin.domain.use_case.auth.*
import com.vaibhav.robin.domain.use_case.database.*
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
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideFirestoreSource() = FirestoreSource()

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    fun provideFirestoreRepository(
        firestore: FirebaseFirestore,
        authRepo: AuthRepository,
        source: FirestoreSource
    ): FirestoreDatabaseRepository = FirestoreRepositoryImpl(firestore, source, authRepo)

    @Provides
    fun provideAuthUseCases(
        repo: AuthRepository
    ) = AuthUseCases(
        isUserAuthenticated = IsUserAuthenticated(repo),
        signInWithEmailPassword = SignInWithEmailPassword(repo),
        signOut = SignOut(repo),
        getAuthState = GetAuthState(repo),
        signUpWithEmailPassword = SignUpWithEmailPassword(repo),
        personalDetailsUpdate = PersonalDetailsUpdate(repo) ,
        getProfileData=GetUserProfileData(repo)
    )

    @Provides
    fun provideDatabaseUseCases(repo: FirestoreDatabaseRepository) =
        DatabaseUseCases(
            updateProfileDateAndGender = UpdateProfileDateAndGender(repo),
            updateAddressAndPhone = UpdateAddressAndPhone(repo),
            initializeProfile = InitializeProfile(repo),
            getProduct = GetProduct(repo),
            getReview = GetReview(repo),
            writeReview = WriteReview(repo),
            getUserReview = GetUserReview(repo),
            addFavourite = AddFavourite(repo),
            removeFavourite = RemoveFavourite(repo),
            checkFavourite = CheckFavourite(repo),
            addCartItem = AddCartItem(repo),
            getCartItem = GetCartItem(repo)
        )
}
