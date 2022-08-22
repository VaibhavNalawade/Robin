package com.vaibhav.robin.data.remote

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirestoreSource {

    suspend inline fun <reified T> fetchFromReferenceToObject(
        document: DocumentReference
    ): Flow<Response<T>> = flow {
        try {
            emit(Response.Loading)
             document.get().await().apply {
                emit(Success(toObject<T>()!!))
            }
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }



    suspend fun fetchFromReference(document: DocumentReference): Flow<Response<Map<String,Any>>> = flow {
        try {
            emit(Response.Loading)
            document.get().await().apply {
                emit(Success(data!!))
            }
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    suspend fun writeToReference(
        document: DocumentReference, data: Any
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            document.set(data).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }

    suspend fun updateToReference(
        document: DocumentReference, data: Map<String,Any>
    ): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            document.update(data).await()
            emit(Success(true))
        } catch (e: Exception) {
            emit(Error(e.message ?: e.toString()))
        }
    }
}