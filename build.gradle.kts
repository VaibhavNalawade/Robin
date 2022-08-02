buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Gradle.androidGradlePlugin)
        classpath(Dependencies.Gradle.kotlinGradlePlugin)
        classpath(Dependencies.Gradle.GOOGLE_SERVICE)
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
