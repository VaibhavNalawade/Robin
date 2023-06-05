package com.vaibhav.robin.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.PaymentData
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : DatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) = tryCatchScaffold {
        source.writeToReference(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!),
            hashMap,
            SetOptions.merge()
        )
    }

    override suspend fun initializeProfile() = tryCatchScaffold {
        source.writeToReference(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!),
            hashMapOf("init" to "Done")
        )
    }

    override suspend fun getProduct(productId: String): Flow<Response<Product>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore
                .collection(COLLECTION_PRODUCTS)
                .document(productId)
        )
    }

    override suspend fun getReviews(productId: String): Flow<Response<List<Review>>> =
        tryCatchScaffold {
            source.fetchFromReferenceToObject(
                firestore.collection(COLLECTION_PRODUCTS)
                    .document(productId)
                    .collection(COLLECTION_REVIEW),
                5
            )
        }

    override suspend fun writeReview(productId: String, review: Review): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.writeToReference(
                firestore
                    .collection(COLLECTION_PRODUCTS)
                    .document(productId)
                    .collection(COLLECTION_REVIEW)
                    .document(auth.getUserUid()!!),
                review
            )
        }

    override suspend fun getUserReview(productId: String): Flow<Response<Review>> =
        tryCatchScaffold {
            source.fetchFromReferenceToObject(
                firestore
                    .collection(COLLECTION_PRODUCTS)
                    .document(productId)
                    .collection(COLLECTION_REVIEW)
                    .document(auth.getUserUid()!!)
            )
        }

    override suspend fun setFavorite(favourite: Favourite) = tryCatchScaffold {
        source.writeToReference(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_FAVOURITE)
                .document(favourite.productID),
            favourite
        )
    }

    override suspend fun removeFavorite(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.deleteDocument(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_FAVOURITE)
                    .document(productId)
            )
        }

    override suspend fun checkProductFavorite(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.checkExits(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_FAVOURITE)
                    .document(productId)
            )
        }

    override suspend fun addCartItem(cartItem: CartItem): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.writeToReference(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_CART)
                    .document(cartItem.cartId),
                cartItem
            )
        }

    override suspend fun getCartItems(): Flow<Response<List<CartItem>>> =
        tryCatchScaffold {
            source.fetchFromReferenceToObject(
                firestore.collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_CART)
            )
        }

    override suspend fun getProducts(): Flow<Response<List<Product>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore.collection(COLLECTION_PRODUCTS)
        )
    }

    override suspend fun deleteCartItem(productId: String): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.deleteDocument(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_CART)
                    .document(productId)
            )
        }

    override suspend fun getCategory(): Flow<Response<List<MainCategory>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore.collection(COLLECTION_CATEGORIES),
        )
    }

    override suspend fun getBrand(): Flow<Response<List<MainBrand>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore.collection(COLLECTION_BRANDS),
        )
    }

    override suspend fun addAddress(address: Address): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.writeToReference(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_ADDRESSES)
                    .document(),
                address
            )
        }

    override suspend fun getAddress(): Flow<Response<List<Address>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_ADDRESSES)
        )
    }

    override suspend fun addPayment(payment: PaymentData): Flow<Response<Boolean>> =
        tryCatchScaffold {
            source.writeToReference(
                firestore
                    .collection(COLLECTION_PROFILE_DATA)
                    .document(auth.getUserUid()!!)
                    .collection(COLLECTION_PAYMENTS)
                    .document(),
                payment
            )
        }

    override suspend fun getPayments(): Flow<Response<List<PaymentData>>> = tryCatchScaffold {
        source.fetchFromReferenceToObject(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_PAYMENTS)
        )
    }

    override suspend fun removePayments(id: String): Flow<Response<Boolean>> = tryCatchScaffold {
        source.deleteDocument(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_PAYMENTS)
                .whereEqualTo(FILED_ID, id)
        )
    }


    override suspend fun removeAddress(id: String): Flow<Response<Boolean>> = tryCatchScaffold {
        source.deleteDocument(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_ADDRESSES)
                .whereEqualTo(FILED_ID, id)
        )
    }
    override suspend fun placeOrder(order: OrderItem): Flow<Response<Boolean>> =tryCatchScaffold {
        source.writeToReference(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_ORDERS)
                .document(order.id),
            order
        )
    }

    override suspend fun listenForOrders()= callbackFlow<Response<List<OrderItem>>> {
        source.listenDocumentChanges(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_ORDERS)
        ).catch {
            trySend(Response.Error(it as Exception))
        }.collect {
            trySend(Response.Success(it.toObjects(OrderItem::class.java)))
        }
    }

    override suspend fun clearCartItems(): Flow<Response<Boolean>> =tryCatchScaffold {
       source.deleteCollection(
           firestore
               .collection(COLLECTION_PROFILE_DATA)
               .document(auth.getUserUid()!!)
               .collection(COLLECTION_CART)
       )
    }


    override suspend fun listenForCartItems() = callbackFlow<Response<List<CartItem>>> {
        source.listenDocumentChanges(
            firestore
                .collection(COLLECTION_PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(COLLECTION_CART)
        ).catch {
            trySend(Response.Error(it as Exception))
        }.collect {
            trySend(Response.Success(it.toObjects(CartItem::class.java)))
        }
    }

    override suspend fun queryProduct(queryProduct: QueryProduct): Flow<Response<List<Product>>> =
        tryCatchScaffold {
            val order: Query.Direction? = when (queryProduct.order) {
                null -> null
                Order.ASCENDING -> Query.Direction.ASCENDING
                else -> Query.Direction.DESCENDING
            }
            var query: Query = firestore.collection(COLLECTION_PRODUCTS)
            queryProduct.brandId?.let { query = query.whereEqualTo(FILED_BRAND_ID, it) }
            queryProduct.categoryId?.let { query = query.whereEqualTo(FILED_CATEGORY_ID, it) }
            order?.let { query = query.orderBy(FILED_MAX_PRICE, order) }
            source.fetchFromReferenceToObject(query)
        }

    private companion object {
        const val COLLECTION_PROFILE_DATA = "ProfileData"
        const val COLLECTION_PAYMENTS = "Payments"
        const val COLLECTION_ADDRESSES = "Addresses"
        const val COLLECTION_PRODUCTS = "Products"
        const val COLLECTION_BRANDS = "Brands"
        const val COLLECTION_CATEGORIES = "Categories"
        const val COLLECTION_CART = "Cart"
        const val COLLECTION_FAVOURITE = "Favourite"
        const val COLLECTION_REVIEW = "Review"
        const val COLLECTION_ORDERS="Orders"

        const val FILED_ID = "id"
        const val FILED_MAX_PRICE = "maxPrice"
        const val FILED_CATEGORY_ID = "categoryId"
        const val FILED_BRAND_ID = "brandId"
    }

    private suspend fun <T> tryCatchScaffold(tryBlock: suspend () -> Flow<Response<T>>): Flow<Response<T>> =
        try {
            tryBlock()
        } catch (e: Exception) {
            flow { emit(Response.Error(e)) }
        }
}