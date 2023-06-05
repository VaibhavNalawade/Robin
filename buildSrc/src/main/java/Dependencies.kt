object Dependencies {

    const val KOTLIN_VERSION = "1.8.21"
    private const val COMPOSE_VERSION = "1.5.0-beta01"
    const val COMPOSE_COMPILER_VERSION = "1.4.7"
    private const val ACTIVITY_VERSION = "1.7.2"
    private const val MDC3_VERSION = "1.2.0-alpha02"
    private const val GSM_VERSION = "4.3.15"
    private const val ACCOMPANIST_VERSION = "0.31.2-alpha"
    private const val LIFECYCLE_VERSION = "2.6.1"
    private const val COROUTINES_VERSION = "1.7.1"
    private const val AGP_VERSION = "8.0.1"
    private const val DAGGER_HILT_VERSION = "2.44"

    const val androidPlugin = "com.android.application"
    const val androidGradle = "com.android.tools.build:gradle:$AGP_VERSION"
    const val coil = "io.coil-kt:coil-compose:2.4.0"
    const val lottieCompose="com.airbnb.android:lottie-compose:6.0.0"
    const val junit = "junit:junit:4.13.2"


    object Kotlin {
        const val androidPlugin = "org.jetbrains.kotlin.android"
        const val kapt = "kotlin-kapt"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"

        object Coroutines {
            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
            const val playServiceSupport =
                "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$COROUTINES_VERSION"
        }
    }

    object Google {
        const val googleServicePlugin = "com.google.gms.google-services"
        const val googleServiceGradle = "com.google.gms:google-services:$GSM_VERSION"

        object Accompanist {
            const val placeholderMaterial =
                "com.google.accompanist:accompanist-placeholder:$ACCOMPANIST_VERSION"
            const val adaptive = "com.google.accompanist:accompanist-adaptive:$ACCOMPANIST_VERSION"
            const val drawablePainter = "com.google.accompanist:accompanist-drawablepainter:$ACCOMPANIST_VERSION"
        }

        object DaggerHilt {
            const val daggerHilt = "com.google.dagger:hilt-android:$DAGGER_HILT_VERSION"
            const val kapt = "com.google.dagger:hilt-android-compiler:$DAGGER_HILT_VERSION"
            const val composeNavigationSupport = "androidx.hilt:hilt-navigation-compose:1.0.0"
            const val daggerHiltGradle =
                "com.google.dagger:hilt-android-gradle-plugin:$DAGGER_HILT_VERSION"
            const val daggerHiltPlugin = "dagger.hilt.android.plugin"
        }

        object Firebase {
            const val BOM = "com.google.firebase:firebase-bom:31.4.0"
            const val firestore = "com.google.firebase:firebase-firestore-ktx"
            const val auth = "com.google.firebase:firebase-auth-ktx"
            const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
            const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.9.4"
            const val crashlyticsPlugin = "com.google.firebase.crashlytics"
        }
    }


    object AndroidX {
        const val navigation = "androidx.navigation:navigation-compose:2.7.0-alpha01"
        const val activity = "androidx.activity:activity-compose:$ACTIVITY_VERSION"

        object Core {
            const val core = "androidx.core:core-ktx:1.10.1"
            const val splashScreen = "androidx.core:core-splashscreen:1.0.1"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
            const val compose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_VERSION"
        }


        object Compose {
            const val ui = "androidx.compose.ui:ui:$COMPOSE_VERSION"
            const val foundation = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
            const val animation="androidx.compose.animation:animation-graphics:$COMPOSE_VERSION"
            const val uiPreview = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
            const val previewTooling = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"

            object Material3 {
                const val main = "androidx.compose.material3:material3:$MDC3_VERSION"
                const val windowsSizeClass =
                    "androidx.compose.material3:material3-window-size-class:$MDC3_VERSION"
            }
        }
    }
}