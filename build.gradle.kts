buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.androidGradle)
        classpath(Dependencies.Kotlin.kotlinGradle)
        classpath(Dependencies.Google.googleServiceGradle)
        classpath (Dependencies.Google.DaggerHilt.daggerHiltGradle)
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
repositories {
    mavenCentral()
}