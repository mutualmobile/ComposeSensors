plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.mavenPublish)
}

android {
    namespace = "com.mutualmobile.composesensors"
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.androidCore)
    implementation(libs.appCompat)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.material)
    implementation(platform(libs.compose.bom))
    testImplementation(libs.junit)
    testImplementation(platform(libs.compose.bom))
}
