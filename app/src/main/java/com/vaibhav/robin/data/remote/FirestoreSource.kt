package com.vaibhav.robin.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend inline fun <reified T> fetchFromReferenceToObject(
        document: DocumentReference
    ): Flow<Response<T>> = flow {

        emit(Response.Loading)
        document.get().await().apply {
            emit(Success(toObject<T>()!!))
        }
    }.catch {
        emit(Error(it as Exception))
    }


    suspend inline fun <reified T> fetchFromReferenceToObject(
        document: CollectionReference,
        limitValue: Long = Long.MAX_VALUE
    ): Flow<Response<List<T>>> = flow {
        try {
            emit(Response.Loading)
            document.limit(limitValue).get().await().apply {

                emit(Success(toObjects(T::class.java)))
            }
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    suspend fun fetchFromReference(document: DocumentReference): Flow<Response<Map<String, Any>>> =
        flow {
            try {
                emit(Response.Loading)
                document.get().await().apply {
                    emit(Success(data!!))
                }
            } catch (e: Exception) {
                emit(Error(e))
            }
        }
    suspend fun  fetchFromReference(document: CollectionReference): Flow<Response<List<Map<String, Any>>>> =
        flow {
            try {
                emit(Response.Loading)
                document.get().await().apply {
                    emit(Success(documents.map { it.data!! }))
                }
            } catch (e: Exception) {
                emit(Error(e))
            }
        }


    suspend fun writeToReference(
        document: DocumentReference, data: Any,
        options: SetOptions? = null
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            if (options == null)
                document.set(data).await()
            else document.set(data, options).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }


    suspend fun updateToReference(
        document: DocumentReference, data: Map<String, Any>
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            document.update(data).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    suspend fun deleteDocument(document: DocumentReference): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            document.delete().await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    suspend fun checkExits(document: DocumentReference): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            emit(Success(document.get().await().exists()))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }

    fun listenDocumentChanges(collectionReference: CollectionReference) =
        collectionReference.snapshots()

    suspend inline fun <reified T> fetchFromReferenceToObject(query: Query) = flow {
        try {
            emit(Response.Loading)
            emit(Success(query.get().await().toObjects(T::class.java)))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }
}