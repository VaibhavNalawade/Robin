package com.vaibhav.robin

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RobinApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        val host="192.168.106.174"
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator(host, 9099)
            //Firebase.database.useEmulator(host,)
        }
        Firebase.database.setPersistenceEnabled(true)
        Firebase.firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
    }
}