package com.vaibhav.robin

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RobinApp : Application() {
    companion object{
        private var isEmulatorNotRunning=true
    }
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        if (BuildConfig.DEBUG&& isEmulatorNotRunning) {
            isEmulatorNotRunning=false
            val host = "192.168.80.174"
            Firebase.firestore.useEmulator(host, 9090)
            Firebase.auth.useEmulator(host, 9099)
        } else Firebase.firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
/*        val a = Firebase.firestore.batch()
        val text = this.resources.openRawResource(R.raw.mock)
            .bufferedReader().use { it.readText() }
        val s= Gson().fromJson(text,Array<Review>::class.java).toList()
        s.forEach {
            val  d= Firebase.firestore.collection("Product").document("D3MjFbzN2Grpl0QRU9cv")
                .collection("Review").document()
            a.set(d,it)
        }
        a.commit()*/
    }
}