// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.7.7"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false

    id("com.google.dagger.hilt.android") version "2.48.1" apply false

    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}