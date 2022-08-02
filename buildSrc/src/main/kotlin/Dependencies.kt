object Dependencies {

    const val KOTLIN_VERSION = "1.7.10"
    const val COMPOSE_VERSION = "1.3.0-alpha02"
    const val COMPOSE_COMPILER_VERSION = "1.3.0-rc01"
    private const val MDC3_VERSION = "1.0.0-alpha15"
    private const val GSM_VERSION = "4.3.13"
    private const val ACCOMPANIST_VERSION = "0.24.12-rc"
    private const val KTOR_VERSION = "2.0.3"
    private const val LIFECYCLE_VERSION = "2.6.0-alpha01"
    private const val COROUTINES_VERSION="1.6.3"
    private const val AGP_VERSION = "7.4.0-alpha08"

    /**
     * Projects Third party Support Section
     */
    object RobinAppSupport {
        const val coil = "io.coil-kt:coil-compose:2.1.0"
    }

    object Kotlin {

        object Ktor {
            const val clientCore = "io.ktor:ktor-client-core:$KTOR_VERSION"
            const val clientAndroid = "io.ktor:ktor-client-android:$KTOR_VERSION"
            const val clientCio = "io.ktor:ktor-client-cio:$KTOR_VERSION"
            const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$KTOR_VERSION"
            const val ktorSerializationKotlinxJson =
                "io.ktor:ktor-serialization-kotlinx-json:$KTOR_VERSION"
        }

        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2"
        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
            const val playServiceSupport = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$COROUTINES_VERSION"
        }
    }

    object Google {
        object Accompanist {
            const val pager = "com.google.accompanist:accompanist-pager:$ACCOMPANIST_VERSION"
            const val pagerIndicator =
                "com.google.accompanist:accompanist-pager-indicators:$ACCOMPANIST_VERSION"
            const val placeholderMaterial =
                "com.google.accompanist:accompanist-placeholder:$ACCOMPANIST_VERSION"
        }
    }

    /**
     * Gradle Plugin Section
     */
    object Gradle {
        const val androidGradlePlugin = "com.android.tools.build:gradle:$AGP_VERSION"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
        const val GOOGLE_SERVICE = "com.google.gms:google-services:$GSM_VERSION"

        object Id {
            const val android = "com.android.application"
            const val kotlinAndroid = "org.jetbrains.kotlin.android"
            const val googleService = "com.google.gms.google-services"
            const val pluginSerialization = "plugin.serialization"
        }
    }

    /**
     * AndroidX Support Library Section
     */
    object AndroidX {


        object Core {
            const val core = "androidx.core:core-ktx:1.9.0-alpha05"
            const val splashScreen = "androidx.core:core-splashscreen:1.0.0-rc01"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
            const val compose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:$LIFECYCLE_VERSION"
        }

        object Paging {
            const val runtime = "androidx.paging:paging-runtime-ktx:3.2.0-alpha01"
            const val compose = "androidx.paging:paging-compose:1.0.0-alpha15"
        }
        object Material{
            const val material="com.google.android.material:material:1.7.0-alpha03"
            const val materialIconExtended = "androidx.compose.material:material-icons-extended:1.2.0-rc01"
            const val material3Compose = "androidx.compose.material3:material3:$MDC3_VERSION"
        }

        /**
         * Jetpack Compose Section
         */
        object Compose {
            const val ui = "androidx.compose.ui:ui:$COMPOSE_VERSION"
            const val foundation="androidx.compose.foundation:foundation:$COMPOSE_VERSION"
            const val UI_TOOLING = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
            const val NAVIGATION = "androidx.navigation:navigation-compose:2.5.0"
            const val ACTIVITY = "androidx.activity:activity-compose:1.6.0-alpha05"
        }
    }

    /**
     * Project Test Section
     */
    object Test {
        const val junit = "junit:junit:4.13.2"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
        const val mockito = "org.mockito.kotlin:mockito-kotlin:4.0.0"

        object AndroidTest {
            const val ext = "androidx.test.ext:junit:1.1.3"
            const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
            const val composeUITest = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"

        }

        object Debug {
            const val uiToolingDebug = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
        }
    }

    /**
     * Firebase Section
     */
    object Firebase {
        const val BOM = "com.google.firebase:firebase-bom:30.3.1"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
        const val database="com.google.firebase:firebase-database-ktx"
        const val auth="com.google.firebase:firebase-auth-ktx"
    }
}