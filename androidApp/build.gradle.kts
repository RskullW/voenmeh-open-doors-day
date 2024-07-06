plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "ru.voenmeh.openday.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "ru.voenmeh.openday.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)

    implementation(libs.dev.icerock.mvvm.core)
    implementation(libs.dev.icerock.mvvm.flow)
    implementation(libs.dev.icerock.mvvm.livedata)

    implementation(libs.androidx.swiperefreshlayout.swiperefreshlayout)
    implementation(libs.io.ktor.okhttp)
    implementation(libs.io.ktor.json)
    implementation(libs.io.ktor.core)

    // TODO: Remove after created KMM module [START]
    implementation(libs.com.google.code.gson)
    implementation(libs.org.jetbrains.kotlinx.datetime)
    // TODO: Remove after created KMM module [STOP]

    implementation(libs.io.koin.core)
    implementation(libs.io.koin.android)
    implementation(libs.org.jetbrains.kotlinx.serialization.json)
    implementation(libs.com.google.android.gms.play.services.location)

    implementation(libs.io.ktor.core)

    implementation(libs.org.jetbrains.kotlinx.serialization.json)

    implementation(libs.io.reactivex.rxjava2.android)
    implementation(libs.io.reactivex.rxjava2.rxjava)

    implementation(libs.com.squareup.picasso)
}