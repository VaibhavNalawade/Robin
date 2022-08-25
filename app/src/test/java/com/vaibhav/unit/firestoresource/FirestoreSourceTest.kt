package com.vaibhav.unit.firestoresource

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class FirestoreSourceTest {
        private val firestore = Firebase.firestore
        private val firestoreDatabase = FirestoreSource()
    @Test
    fun `write To Database Test`()= runTest {
        val ref = firestore.collection("Test").document("Test")
        firestoreDatabase.writeToReference(ref, hashMapOf("test" to "OK")).collect{
            when(it){
                is Success-> assert(true)
                is Error -> throw Exception(it.message)
                Loading -> {}
            }
        }
    }

    @Test
    fun `Read from Database Test`()= runTest {
        val ref = firestore.collection("Test").document("Test")
        firestoreDatabase.fetchFromReference(ref,).collect{
            when(it){
                is Success-> {
                    assert(it.data?.get("test")?.equals("OK")?:false)
                }
                is Error -> throw Exception(it.message)
                Loading -> {}
            }
        }
    }

    @Test
    fun `read from Database Serialization test`()=runTest {
        val ref = firestore.collection("Test").document("Test")
        firestoreDatabase.fetchFromReferenceToObject<TestClass>(ref,).collect{
            when(it){
                is Success-> {
                   if ( it.data.test.equals("OK"))
                       assert(true)
                    else assert(false)
                }
                is Error -> throw Exception(it.message)
                Loading -> {}
            }
        }
    }
    data class TestClass (val test:String?=null)
}