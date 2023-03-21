object Dependencies {

    const val KOTLIN_VERSION = "1.8.10"
    const val COMPOSE_VERSION = "1.4.0-rc01"
    const val COMPOSE_COMPILER_VERSION = "1.4.3"
    private const val ACTIVITY_VERSION="1.6.0-beta01"
    private const val MDC3_VERSION = "1.1.0-alpha08"
    private const val GSM_VERSION = "4.3.15"
    private const val ACCOMPANIST_VERSION = "0.29.1-alpha"
    private const val LIFECYCLE_VERSION = "2.6.0"
    private const val COROUTINES_VERSION = "1.6.4"
    private const val AGP_VERSION = "8.1.0-alpha09"
    private const val DAGGERHILT_VERSION = "2.44"

    const val androidPlugin = "com.android.application"
    const val androidGradle = "com.android.tools.build:gradle:$AGP_VERSION"
    const val coil = "io.coil-kt:coil-compose:2.2.2"
    const val junit="junit:junit:4.13.2"




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
        }

        object DaggerHilt {
            const val daggerHilt = "com.google.dagger:hilt-android:$DAGGERHILT_VERSION"
            const val kapt = "com.google.dagger:hilt-android-compiler:$DAGGERHILT_VERSION"
            const val composeNavigationSupport = "androidx.hilt:hilt-navigation-compose:1.0.0"
            const val daggerHiltGradle = "com.google.dagger:hilt-android-gradle-plugin:$DAGGERHILT_VERSION"
            const val daggerHiltPlugin = "dagger.hilt.android.plugin"
        }

        object Firebase {
            const val BOM = "com.google.firebase:firebase-bom:31.2.3"
            const val firestore = "com.google.firebase:firebase-firestore-ktx"
            const val auth = "com.google.firebase:firebase-auth-ktx"
        }
    }



    object AndroidX {
        const val navigation = "androidx.navigation:navigation-compose:2.6.0-alpha07"
        const val activity = "androidx.activity:activity-compose:$ACTIVITY_VERSION"

        object Core {
            const val core = "androidx.core:core-ktx:1.9.0"
            const val splashScreen = "androidx.core:core-splashscreen:1.0.0"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
            const val compose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_VERSION"
        }


        object Compose {
            const val ui = "androidx.compose.ui:ui:$COMPOSE_VERSION"
            const val foundation = "androidx.compose.foundation:foundation:$COMPOSE_VERSION"
            const val uiPrview = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
            const val previewTooling = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"

            object Material3 {
                const val main = "androidx.compose.material3:material3:$MDC3_VERSION"
                const val windowsSizeClass =
                    "androidx.compose.material3:material3-window-size-class:$MDC3_VERSION"
            }
        }
    }
}