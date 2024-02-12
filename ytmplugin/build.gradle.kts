plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":common"))

    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("com.github.teamnewpipe:NewPipeExtractor:v0.22.7")
}