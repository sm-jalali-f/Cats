plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.freez.cat.catbreed.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation(project(":core:util"))
    implementation(project(":catbreed:domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // coroutines test
    testImplementation(libs.kotlinx.coroutines.test)

    // mocking dependencies
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    // ui
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // load image from url
    implementation(libs.coil.compose)
    implementation(libs.coil3.coil.network.okhttp)
    // navigation
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}