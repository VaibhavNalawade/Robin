buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.androidGradlePlugin)
        classpath(Dependencies.Gradle.kotlinGradlePlugin)
        classpath(Dependencies.Gradle.googleService)
        classpath (Dependencies.Gradle.daaggerHilt)
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
