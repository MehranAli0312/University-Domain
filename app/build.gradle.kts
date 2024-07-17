plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safeargs)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.genesis"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.genesis"
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    jetpack navigation
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.navigation.ui)

//    koin di
    implementation(libs.koin)
    implementation(libs.koin.android)

    //Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)


    //coil
    implementation(libs.coil)

//
    implementation(libs.ssp)
    implementation(libs.sdp)

}