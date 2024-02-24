plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "tel.jeelpa.musicplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "tel.jeelpa.musicplayer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation("androidx.media3:media3-exoplayer:1.2.1")

    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation(project(":common"))
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("io.coil-kt:coil:2.5.0")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")


    // plugger
    implementation(project(":plugger:plugger"))

    // TODO: REMOVE IN RELEASE
    implementation(project(":ytmplugin"))
    implementation(project(":soundcloudplugin"))
    debugImplementation("com.squareup.leakcanary:leakcanary-android:3.0-alpha-1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:rules:1.5.0")
}

kapt {
    correctErrorTypes = true
}