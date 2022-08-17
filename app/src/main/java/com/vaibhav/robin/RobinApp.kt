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
    override fun onCreate() {
        super.onCreate()
            Firebase.initialize(this)
            if (BuildConfig.DEBUG) {
                val host = "192.168.69.69"
                Firebase.firestore.useEmulator(host, 8080)
                Firebase.auth.useEmulator(host, 9099)
            } else Firebase.firestore.firestoreSettings = firestoreSettings {
                isPersistenceEnabled = true
            }
    }
}