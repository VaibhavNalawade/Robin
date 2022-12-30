plugins {
    id(Dependencies.Gradle.Id.android)
    id(Dependencies.Gradle.Id.kotlinAndroid)
    id(Dependencies.Gradle.Id.googleService)
    id(Dependencies.Gradle.Id.daggerHilt)
    kotlin(Dependencies.Gradle.Id.kapt)
}

android {
    compileSdk = RobinConfig.compileSdk
    namespace = "com.vaibhav.robin"
    defaultConfig {
        applicationId = RobinConfig.applicationId
        minSdk = RobinConfig.minsdk
        targetSdk = RobinConfig.targetSdk
        versionCode = RobinConfig.versionCode
        versionName = RobinConfig.versionName
        testInstrumentationRunner = RobinConfig.testRunner

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug{

        }
        create("Admin"){

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.COMPOSE_COMPILER_VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(Dependencies.Kotlin.Coroutines.android)
    implementation(Dependencies.Kotlin.Coroutines.playServiceSupport)

    implementation(Dependencies.Google.Accompanist.pager)
    implementation(Dependencies.Google.Accompanist.pagerIndicator)
    implementation(Dependencies.Google.Accompanist.placeholderMaterial)
    implementation(Dependencies.Google.Accompanist.adaptive)
    implementation(Dependencies.Google.Accompanist.flowLayout)


    implementation(platform(Dependencies.Firebase.BOM))
    implementation(Dependencies.Firebase.firestore)
    implementation(Dependencies.Firebase.auth)

    implementation(Dependencies.RobinAppSupport.coil)

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.splashScreen)

    implementation(Dependencies.AndroidX.Lifecycle.runtime)
    implementation(Dependencies.AndroidX.Lifecycle.compose)

    implementation(Dependencies.AndroidX.Paging.compose)
    implementation(Dependencies.AndroidX.Paging.runtime)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.foundation)
    implementation(Dependencies.AndroidX.Compose.UI_TOOLING)
    implementation(Dependencies.AndroidX.Compose.NAVIGATION)
    implementation(Dependencies.AndroidX.Compose.ACTIVITY)

    implementation(Dependencies.AndroidX.Material.material)
    implementation(Dependencies.AndroidX.Material.material3Compose)
    implementation(Dependencies.AndroidX.Material.material3ComposeWindowsSizeClass)


    implementation(Dependencies.Google.DaggerHilt.daggerHilt)
    kapt(Dependencies.Google.DaggerHilt.kapt)
    implementation (Dependencies.Google.DaggerHilt.composeNavigationSupport)

    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.coroutines)
    testImplementation(Dependencies.Test.mockito)
    testImplementation(Dependencies.Test.robolectric)
    androidTestImplementation(Dependencies.Test.AndroidTest.core)
    androidTestImplementation(Dependencies.Test.AndroidTest.ext)
    androidTestImplementation(Dependencies.Test.AndroidTest.espresso)
    androidTestImplementation(Dependencies.Test.AndroidTest.composeUITest)
    debugImplementation(Dependencies.Test.Debug.uiToolingDebug)
    debugImplementation(Dependencies.Test.Debug.uiTestManifest)
}