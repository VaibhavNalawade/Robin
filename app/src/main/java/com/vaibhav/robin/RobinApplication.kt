package com.vaibhav.robin

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RobinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        Firebase.firestore.firestoreSettings = firestoreSettings {
            isPersistenceEnabled = true
        }
    }
}