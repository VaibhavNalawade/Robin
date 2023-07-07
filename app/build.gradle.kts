import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Dependencies.androidPlugin)
    id(Dependencies.Kotlin.androidPlugin)
    id(Dependencies.Google.googleServicePlugin)
    id(Dependencies.Google.DaggerHilt.daggerHiltPlugin)
    id(Dependencies.Kotlin.kapt)
    id(Dependencies.Google.Firebase.crashlyticsPlugin)
}

android {
    compileSdk = RobinConfig.compileSdk
    namespace = RobinConfig.namespace
    defaultConfig {
        applicationId = RobinConfig.applicationId
        minSdk = RobinConfig.minsdk
        targetSdk = RobinConfig.targetSdk
        versionCode = RobinConfig.versionCode
        versionName = RobinConfig.versionName
        testInstrumentationRunner = RobinConfig.instrumentRunner
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.COMPOSE_COMPILER_VERSION
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(Dependencies.Kotlin.Coroutines.android)
    implementation(Dependencies.Kotlin.Coroutines.playServiceSupport)

    implementation(Dependencies.Google.Accompanist.placeholderMaterial)
    implementation(Dependencies.Google.Accompanist.adaptive)
    implementation(platform(Dependencies.Google.Firebase.BOM))
    implementation(Dependencies.Google.Firebase.firestore)
    implementation(Dependencies.Google.Firebase.auth)
    implementation(Dependencies.Google.Firebase.crashlytics)
    implementation(Dependencies.Google.DaggerHilt.daggerHilt)
    kapt(Dependencies.Google.DaggerHilt.kapt)
    implementation(Dependencies.Google.DaggerHilt.composeNavigationSupport)

    implementation(Dependencies.coil)
    implementation(Dependencies.lottieCompose)

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.splashScreen)
    implementation(Dependencies.AndroidX.Lifecycle.runtime)
    implementation(Dependencies.AndroidX.Lifecycle.compose)
    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.foundation)
    implementation(Dependencies.AndroidX.Compose.animation)
    implementation(Dependencies.AndroidX.Compose.uiPreview)
    debugImplementation(Dependencies.AndroidX.Compose.previewTooling)
    implementation(Dependencies.AndroidX.navigation)
    implementation(Dependencies.AndroidX.activity)
    implementation(Dependencies.AndroidX.Compose.Material3.main)
    implementation(Dependencies.AndroidX.Compose.Material3.windowsSizeClass)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.AndroidX.Compose.testJunit)
    debugImplementation(Dependencies.AndroidX.Compose.testManifest)
}