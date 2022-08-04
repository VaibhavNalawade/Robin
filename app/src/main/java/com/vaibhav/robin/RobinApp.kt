package com.vaibhav.robin

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import io.grpc.android.BuildConfig

class RobinApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.database.setPersistenceEnabled(true)
        Firebase.firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
        if (BuildConfig.DEBUG) {
            Firebase.auth.useEmulator("10.0.0.2", 9090)
        }
    }
}