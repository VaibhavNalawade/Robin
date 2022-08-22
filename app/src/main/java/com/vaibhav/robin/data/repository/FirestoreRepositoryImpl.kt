package com.vaibhav.robin.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : FirestoreDatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) =
        source.updateToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!),
            hashMap
        )

    override suspend fun initializeProfile() =
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!),
            hashMapOf("init" to "Done")
        )

    override suspend fun getProduct(productId: String): Flow<Response<Product>> =
        source.fetchFromReferenceToObject<Product>(firestore.collection("Product").document(productId)).also { Log.e("TAG",
            productId
        ) }
}