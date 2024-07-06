import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.com.jakewharton.threetenabp)
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.material)
            implementation(libs.androidx.constraintlayout)
            implementation(libs.androidx.activity)
            implementation(libs.io.ktor.okhttp)
            implementation(libs.io.koin.android)
            implementation(libs.com.journeyapps.zxing.android.embedded)
            implementation(libs.com.google.android.gms.play.services.location)
            implementation(libs.firebase.firestore)
        }

        commonMain.dependencies {
            implementation(libs.dev.icerock.mvvm.core)
            implementation(libs.dev.icerock.mvvm.flow)
            implementation(libs.dev.icerock.mvvm.livedata)
            implementation(libs.io.ktor.core)
            implementation(libs.io.ktor.json)
            implementation(libs.io.ktor.cio)
            implementation(libs.io.koin.core)
            implementation(libs.org.jetbrains.kotlinx.serialization.json)
            implementation(libs.org.jetbrains.kotlinx.datetime)
        }

        iosMain.dependencies {
            implementation(libs.io.ktor.darwin)
            implementation(libs.org.jetbrains.kotlinx.serialization.json)
            implementation(libs.io.koin.core)
        }
    }
}

val facultyA: String = gradleLocalProperties(rootDir).getProperty("facultyA")
val facultyE: String = gradleLocalProperties(rootDir).getProperty("facultyE")
val facultyI: String = gradleLocalProperties(rootDir).getProperty("facultyI")
val facultyO: String = gradleLocalProperties(rootDir).getProperty("facultyO")
val facultyR: String = gradleLocalProperties(rootDir).getProperty("facultyR")
val facultySPO: String = gradleLocalProperties(rootDir).getProperty("facultySPO")
val facultyVUC: String = gradleLocalProperties(rootDir).getProperty("facultyVUC")

android {
    namespace = "ru.voenmeh.openday"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
        buildConfigField("int", "VERSION_CODE", "1")
        buildConfigField("String", "VERSION_NAME", "\"1.0\"")
        buildConfigField("String", "FACULTY_A", facultyA)
        buildConfigField("String", "FACULTY_E", facultyE)
        buildConfigField("String", "FACULTY_I", facultyI)
        buildConfigField("String", "FACULTY_O", facultyO)
        buildConfigField("String", "FACULTY_R", facultyR)
        buildConfigField("String", "FACULTY_SPO", facultySPO)
        buildConfigField("String", "FACULTY_VUC", facultyVUC)

        externalNativeBuild {
            ndkBuild {
                cppFlags += ""
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    externalNativeBuild {
        ndkBuild {
            path = file("src/androidMain/kotlin/ru/voenmeh/openday/jni/Android.mk")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}
