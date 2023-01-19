package com.vaibhav.robin.data.repository


import android.util.Log
import com.google.firebase.firestore.AggregateQuery
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.QueryProduct
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.AuthRepository
import com.vaibhav.robin.domain.repository.DatabaseRepository
import com.vaibhav.robin.presentation.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : DatabaseRepository {
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
            firestore.collection("Products").document(productId)
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }


    override suspend fun getReviews(productId: String): Flow<Response<List<Review>>> = try {
        source.fetchFromReferenceToObject(
            firestore.collection("Products").document(productId).collection("Review"),
            limitValue = 5
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }


    override suspend fun writeReview(productId: String, review: Review): Flow<Response<Boolean>> =
        try {
            source.writeToReference(
                firestore.collection("Products").document(productId).collection("Review")
                    .document(auth.getUserUid()!!), review
            )
        } catch (e: Exception) {
            flow { emit(Response.Error(e)) }
        }

    override suspend fun getUserReview(productId: String): Flow<Response<Review>> = try {
        source.fetchFromReferenceToObject(
            firestore.collection("Products").document(productId).collection("Review")
                .document(auth.getUserUid()!!)
        )
    } catch (e: Exception) {
        flow { emit(Response.Error(e)) }
    }

    override suspend fun setFavorite(productId: String, favourite: Favourite) = tryCatchScaffold {
        source.writeToReference(
            firestore.collection("ProfileData").document(auth.getUserUid()!!)
                .collection("Favourite").document(productId), favourite
        )
    }

    override suspend fun removeFavorite(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.deleteDocument(
                firestore.collection("ProfileData").document(auth.getUserUid()!!)
                    .collection("Favourite").document(productId)
            )
        }

    override suspend fun checkProductFavorite(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.checkExits(
                firestore.collection("ProfileData").document(auth.getUserUid()!!)
                    .collection("Favourite").document(productId)
            )
        }

    override suspend fun addCartItem(cartItem: CartItem): Flow<Response<Boolean>> =
        tryCatchScaffold {
            val cartItem = cartItem.copy(cartId = UUID.randomUUID().toString())
            source.writeToReference(
                firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Cart")
                    .document(
                        cartItem.cartId
                    ),
                cartItem
            )
        }

    override suspend fun getCartItems(): Flow<Response<List<CartItem>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore.collection("ProfileData").document(auth.getUserUid()!!).collection("Cart"),
            limitValue = 100
        )
    }

    override suspend fun getProducts(): Flow<Response<List<Product>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(firestore.collection("Products"), 50)
    }

    override suspend fun listenForCartItems() = callbackFlow<Response<List<CartItem>>> {
        source.listenDocumentChanges(
            firestore.collection("ProfileData")
                .document(auth.getUserUid()!!)
                .collection("Cart")
        )
            .collect {
                trySend(Response.Success(it.toObjects(CartItem::class.java)))
            }
    }

    override suspend fun deleteCartItem(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.deleteDocument(
                firestore.collection("ProfileData")
                    .document(auth.getUserUid()!!)
                    .collection("Cart").document(productId)
            )
        }

    override suspend fun getCategory(): Flow<Response<List<MainCategory>>> =
        source.fetchFromReferenceToObject(firestore.collection("Categories"), 100L)


    override suspend fun getBrand(): Flow<Response<List<MainBrand>>> =
        source.fetchFromReferenceToObject(firestore.collection("Brands"), 100L)

    override suspend fun queryProduct(queryProduct: QueryProduct): Flow<Response<List<Product>>> {
        val order: Query.Direction?

        order = if (queryProduct.order==null)
            null
        else if (queryProduct.order == Order.ASCENDING) {
            Query.Direction.ASCENDING
        } else {
            Query.Direction.DESCENDING
        }
        var query: Query = firestore.collection("Products")
        queryProduct.brandId?.let { query = query.whereEqualTo("brandId", it) }
        queryProduct.categoryId?.let { query = query.whereEqualTo("categoryId", it) }
        order?.let { query = query.orderBy("maxPrice", order) }
        return source.fetchFromReferenceToObject(query)
    }


    private suspend fun <T> tryCatchScaffold(tryBlock: suspend () -> Flow<Response<T>>): Flow<Response<T>> =
        try {
            tryBlock()
        } catch (e: Exception) {
            flow { emit(Response.Error(e)) }
        }
}