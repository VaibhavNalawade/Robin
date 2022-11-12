package com.vaibhav.robin.data.repository


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.log

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : FirestoreDatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) = try {
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!),
            hashMap,
            options = SetOptions.merge()
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }

    override suspend fun initializeProfile() = try {
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!),
            hashMapOf("init" to "Done")
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }

    override suspend fun getProduct(productId: String): Flow<Response<Product>> = try {
        source.fetchFromReferenceToObject(
            firestore.collection("Product").document(productId)
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }


    override suspend fun getReviews(productId: String): Flow<Response<List<Review>>> = try {
        source.fetchFromReferenceToObject(
            firestore.collection("Product").document(productId).collection("Review"),
            limitValue = 5
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }


    override suspend fun writeReview(productId: String, review: Review): Flow<Response<Boolean>> =
        try {
            source.writeToReference(
                firestore.collection("Product").document(productId).collection("Review")
                    .document(auth.getUserUid()!!), review
            )
        } catch (e: Exception) {
            flow { emit(Response.Error(e)) }
        }

    override suspend fun getUserReview(productId: String): Flow<Response<Review>> = try {
        source.fetchFromReferenceToObject(
            firestore.collection("Product").document(productId).collection("Review").document(auth.getUserUid()!!)
        )
    }
    catch (e:Exception){
        flow { emit(Response.Error(e)) }
    }

    override suspend fun setFavorite(productId: String, favourite: Favourite) = tryCatchScaffold {
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Favourite").document(productId),favourite
        )
    }

    override suspend fun removeFavorite(productId: String): Flow<Response<Boolean>> = tryCatchScaffold {
            source.deleteDocument(
                firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Favourite").document(productId)
            )
        }

    override suspend fun checkProductFavorite(productId: String): Flow<Response<Boolean>> = tryCatchScaffold {
        source.checkExits(
            firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Favourite").document(productId)
        )
    }

    override suspend fun addCartItem(productId: String,cartItem: CartItem): Flow<Response<Boolean>> = tryCatchScaffold {
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Cart").document(productId),
            cartItem
        )
    }

    override suspend fun getCartItems(): Flow<Response<List<CartItem>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Cart"), limitValue = 4
        )
    }

    override suspend fun getProducts(): Flow<Response<List<Product>>> =tryCatchScaffold {
        source.fetchFromReferenceToObject(firestore.collection("Product"),50)
    }


    private suspend fun <T>tryCatchScaffold(tryBlock:suspend ()->Flow<Response<T>>):Flow<Response<T>> =
        try {
           tryBlock()
        }
        catch (e:Exception){
            flow { emit(Response.Error(e)) }
        }
}