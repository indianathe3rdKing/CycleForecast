plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {

    namespace = "com.devbub.cycleforecast"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {

        applicationId = "com.devbub.cycleforecast"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources=true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig=true
    }
}

dependencies {
    val voyagerVersion = "1.1.0-beta02"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation("io.insert-koin:koin-androidx-compose")

    // Networking

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // Image loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Location services
    implementation(libs.location.services)

    // ViewModel

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")

    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:${voyagerVersion}")
// TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:${voyagerVersion}")

// DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

// WorkManager
    implementation("androidx.work:work-runtime-ktx:2.9.0")

// Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:${voyagerVersion}")

    //Lottie
    implementation("com.airbnb.android:lottie-compose:6.0.0")

    //SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.0-beta02")
}