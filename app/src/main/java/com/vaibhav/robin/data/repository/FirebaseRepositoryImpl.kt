package com.vaibhav.robin.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.Favourite
import com.vaibhav.robin.data.models.MainBrand
import com.vaibhav.robin.data.models.MainCategory
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
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val source: FirestoreSource,
    private val auth: AuthRepository
) : DatabaseRepository {
    override suspend fun updateProfile(hashMap: HashMap<String, Any>) =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!),
            hashMap,
            SetOptions.merge()
        )

    override suspend fun initializeProfile() =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!),
            hashMapOf("init" to "Done")
        )

    override suspend fun getProduct(productId: String): Flow<Response<Product>> =
        source.fetchFromReferenceToObject(
            firestore
                .collection(PRODUCTS)
                .document(productId)
        )

    override suspend fun getReviews(productId: String): Flow<Response<List<Review>>> =
        source.fetchFromReferenceToObject(
            firestore.collection(PRODUCTS)
                .document(productId)
                .collection(REVIEW),
            5
        )

    override suspend fun writeReview(productId: String, review: Review): Flow<Response<Boolean>> =
        source.writeToReference(
            firestore
                .collection(PRODUCTS)
                .document(productId)
                .collection(REVIEW)
                .document(auth.getUserUid()!!),
            review
        )

    override suspend fun getUserReview(productId: String): Flow<Response<Review>> =
        source.fetchFromReferenceToObject(
            firestore
                .collection(PRODUCTS)
                .document(productId)
                .collection(REVIEW)
                .document(auth.getUserUid()!!)
        )

    override suspend fun setFavorite(favourite: Favourite) =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(FAVOURITE)
                .document(favourite.productID),
            favourite
        )

    override suspend fun removeFavorite(productId: String): Flow<Response<Boolean>> =
        source.deleteDocument(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(FAVOURITE)
                .document(productId)
        )

    override suspend fun checkProductFavorite(productId: String): Flow<Response<Boolean>> =
        source.checkExits(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(FAVOURITE)
                .document(productId)
        )

    override suspend fun addCartItem(cartItem: CartItem): Flow<Response<Boolean>> =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(CART)
                .document(cartItem.cartId),
            cartItem
        )

    override suspend fun getCartItems(): Flow<Response<List<CartItem>>> =
        source.fetchFromReferenceToObject(
            firestore.collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(CART),
            100
        )

    override suspend fun getProducts(): Flow<Response<List<Product>>> =
        source.fetchFromReferenceToObject(
            firestore.collection(PRODUCTS),
            50
        )

    override suspend fun deleteCartItem(productId: String): Flow<Response<Boolean>> =
        source.deleteDocument(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(CART)
                .document(productId)
        )

    override suspend fun getCategory(): Flow<Response<List<MainCategory>>> =
        source.fetchFromReferenceToObject(
            firestore.collection(CATEGORIES),
            100L
        )

    override suspend fun getBrand(): Flow<Response<List<MainBrand>>> =
        source.fetchFromReferenceToObject(
            firestore.collection(BRANDS),
            100L
        )

    override suspend fun addAddress(address: Map<String, Any>): Flow<Response<Boolean>> =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(ADDRESSES)
                .document(),
            address
        )

    override suspend fun getAddress(): Flow<Response<List<Map<String, Any>>>> =
        source.fetchFromReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(ADDRESSES)
        )

    override suspend fun addPayment(payment: PaymentData): Flow<Response<Boolean>> =
        source.writeToReference(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(PAYMENTS)
                .document(),
            payment
        )

    override suspend fun getPayments(): Flow<Response<List<PaymentData>>> =
        source.fetchFromReferenceToObject(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(PAYMENTS),
            50
        )

    override suspend fun removePayments(id: String): Flow<Response<Boolean>> =
       source.deleteDocument(
           firestore
               .collection(PROFILE_DATA)
               .document(auth.getUserUid()!!)
               .collection(PAYMENTS)
               .whereEqualTo("id",id)
       )


    override suspend fun removeAddress(id: String): Flow<Response<Boolean>> =
        source.deleteDocument(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(ADDRESSES)
                .whereEqualTo("id",id)
        )


    override suspend fun listenForCartItems() = callbackFlow<Response<List<CartItem>>> {
        source.listenDocumentChanges(
            firestore
                .collection(PROFILE_DATA)
                .document(auth.getUserUid()!!)
                .collection(CART)
        ).catch {
            trySend(Response.Error(it as Exception))
        }.collect {
            trySend(Response.Success(it.toObjects(CartItem::class.java)))
        }
    }

    override suspend fun queryProduct(queryProduct: QueryProduct): Flow<Response<List<Product>>> {

        val order: Query.Direction? = when (queryProduct.order) {
            null -> null
            Order.ASCENDING -> Query.Direction.ASCENDING
            else -> Query.Direction.DESCENDING
        }
        var query: Query = firestore.collection(PRODUCTS)
        queryProduct.brandId?.let { query = query.whereEqualTo("brandId", it) }
        queryProduct.categoryId?.let { query = query.whereEqualTo("categoryId", it) }
        order?.let { query = query.orderBy("maxPrice", order) }
        return source.fetchFromReferenceToObject(query)
    }

    private companion object {
        const val PROFILE_DATA = "ProfileData"
        const val PAYMENTS = "Payments"
        const val ADDRESSES = "addresses"
        const val PRODUCTS = "Products"
        const val BRANDS = "Brands"
        const val CATEGORIES = "Categories"
        const val CART = "Cart"
        const val FAVOURITE = "Favourite"
        const val REVIEW = "Review"
    }
}