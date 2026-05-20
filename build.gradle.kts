plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {

    namespace = "com.example.nammayantrashare"

    compileSdk = 36

    defaultConfig {

        applicationId = "com.example.nammayantrashare"

        minSdk = 24

        targetSdk = 36

        versionCode = 1

        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        )
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")

    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation("com.google.android.material:material:1.12.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))

    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("com.google.firebase:firebase-storage-ktx")

    implementation("com.google.firebase:firebase-messaging-ktx")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.razorpay:checkout:1.6.40")
    implementation("com.github.bumptech.glide:glide:4.16.0")
}
