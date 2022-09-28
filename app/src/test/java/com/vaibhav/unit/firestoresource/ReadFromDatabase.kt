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
class ReadFromDatabase {
        private val firestore = Firebase.firestore
        private val firestoreDatabase = FirestoreSource()
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
}