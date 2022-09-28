package com.vaibhav.unit.firestoresource

import com.google.android.gms.common.util.JsonUtils
import com.vaibhav.robin.R
import com.google.common.io.Resources
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.data.remote.FirestoreSource
import com.vaibhav.robin.domain.model.Response
import io.grpc.internal.JsonUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.InputStream


@Config(sdk = [32])
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class WriteTest {
    private val firestore = Firebase.firestore
    private val firestoreDatabase = FirestoreSource()

    @Test
    fun `write To Database Test`()= runTest {
        val ref = firestore.collection("Test").document("Test").collection("sup").document()
        firestoreDatabase.writeToReference(ref, Review()).collect{
            when(it){
                is Response.Success -> assert(true)
                is Response.Error -> throw Exception(it.message)
                Response.Loading -> {}
            }
        }
    }

    @Test
    fun writemock(){

    }
}