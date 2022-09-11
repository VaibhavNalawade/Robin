package com.vaibhav.unit.firestoresource

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
class ReadFromDatabaseToObject {
    private val firestore = Firebase.firestore
    private val firestoreDatabase = FirestoreSource()
    @Test
    fun `read from Database Serialization test`()= runTest {
        val ref = firestore.collection("Test").document("Test")
        firestoreDatabase.fetchFromReferenceToObject<TestClass>(ref,).collect{
            when(it){
                is Response.Success -> {
                    if ( it.data.test.equals("OK"))
                        assert(true)
                    else assert(false)
                }
                is Response.Error -> throw Exception(it.message)
                Response.Loading -> {}
            }
        }
    }
    data class TestClass (val test:String?=null)
}