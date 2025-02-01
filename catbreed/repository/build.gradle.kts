plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.freez.cat.catbreed.repository"
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
}
kapt {
    correctErrorTypes = true
}
dependencies {
    implementation(project(":core:util"))
    implementation(project(":datasource:remote:theCatApi"))
    implementation(project(":datasource:local:roomDatabase"))
    implementation(project(":catbreed:domain"))
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // For mocking dependencies
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.kotlin)

    // For Kotlin coroutines test
    testImplementation (libs.kotlinx.coroutines.test)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation (libs.retrofit)
}