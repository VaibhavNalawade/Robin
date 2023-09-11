import org.gradle.kotlin.dsl.libs

plugins {
    alias(libs.plugins.android)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.googleservice)
    alias(libs.plugins.daggerhilt)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vaibhav.robin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vaibhav.robin"
        minSdk = 21
        targetSdk = 34
        versionCode = 10
        versionName = "alpha-0.82"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = true
            isShrinkResources = true

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(libs.bundles.kotlin.couroutines)
    implementation(libs.bundles.daggerhilt)
    ksp(libs.daggerhilt.kapt)
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)
    testImplementation(libs.junit)
    testImplementation(libs.bundles.mockito)
    androidTestImplementation(libs.bundles.compose.test)
    debugImplementation(libs.bundles.compose.debug)
}