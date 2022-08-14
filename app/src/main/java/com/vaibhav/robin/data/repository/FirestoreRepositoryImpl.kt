package com.vaibhav.robin.data.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : FirestoreDatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) =
        source.updateToReference(
            firestore.collection("ProfileData").document(Firebase.auth.currentUser!!.uid),
            hashMap
        )

    override suspend fun initializeProfile() =
        source.writeToReference(
            firestore.collection("ProfileData").document(Firebase.auth.currentUser!!.uid),
            hashMapOf("init" to "Done")
        )
}