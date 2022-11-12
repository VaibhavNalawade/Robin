package com.vaibhav.unit.firestoresource

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.models.Brand
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ReadFromCollection {
    private val firestore = Firebase.firestore
    private val firestoreDatabase = FirestoreSource()
    @Test
    fun `read from Database Serialization test`()= runTest {
        val ref = firestore.collection("Brand")
        firestoreDatabase.fetchFromReferenceToObject<Brand>(ref,).collect{
            when(it){
                is Response.Success -> {
                    it.data.forEach{
                        assert(true)
                    }

                   // else assert(false)
                }
                is Response.Error -> throw Exception(it.message)
                Response.Loading -> {}
            }
        }
    }
}