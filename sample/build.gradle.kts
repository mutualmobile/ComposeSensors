import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composePlugin)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.mutualmobile.sample"
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        applicationId = "com.mutualmobile.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    signingConfigs {
        val propertiesFile = File("keystore.properties")
        if (propertiesFile.exists()) {
            create("release") {
                // Load keystore
                val keystoreProperties = Properties().apply {
                    load(propertiesFile.reader())
                }
                storeFile = File(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            runCatching {
                signingConfigs.getByName("release")
            }.onSuccess { config ->
                signingConfig = config
            }
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.junit.android)
    androidTestImplementation(libs.junit.compose)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.compose.ui.testManifest)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.android.lifecycle.runtime)
    implementation(libs.androidCore)
    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.splashScreen)
    implementation(platform(libs.compose.bom))
    implementation(project(":composesensors"))
    testImplementation(libs.junit)
    testImplementation(platform(libs.compose.bom))
}
