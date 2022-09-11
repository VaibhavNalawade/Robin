package com.vaibhav.robin.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : FirestoreDatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) = source.updateToReference(
        firestore.collection("ProfileData").document(auth.getUserUid()!!), hashMap
    )

    override suspend fun initializeProfile() = source.writeToReference(
        firestore.collection("ProfileData").document(auth.getUserUid()!!),
        hashMapOf("init" to "Done")
    )

    override suspend fun getProduct(productId: String): Flow<Response<Product>> =
        source.fetchFromReferenceToObject(
            firestore.collection("Product").document(productId)
        )


    override suspend fun getReview(productId: String): Flow<Response<List<Review>>> =
        source.fetchFromReferenceToObject(
            firestore.collection("Product").document(productId).collection("Review")
        )

    override suspend fun writeReview(productId: String, review: Review): Flow<Response<Boolean>> =
        source.writeToReference(
            firestore.collection("Product").document(productId).collection("Review")
                .document(auth.getUserUid()!!), review
        )
}