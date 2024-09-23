plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.walkS.yiprogress"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.walkS.yiprogress"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.utilcode)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)


    implementation(libs.kotlinx.serialization.json.v170)
    // optional - Jetpack Compose integration
    implementation(libs.paging.compose)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.v130beta)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.tooling.preview.desktop)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation (libs.com.google.accompanist.accompanist.pager2) // Pager
    implementation (libs.accompanist.pager.indicators) // Pager Indicators
    implementation(libs.accompanist.drawablepainter)
    implementation (libs.androidx.room.room.runtime)
    implementation(libs.androidx.appcompat.resources)
    kapt (libs.androidx.room.compiler)
    implementation(libs.androidx.fragment.ktx)
    implementation ("com.alibaba.fastjson2:fastjson2:2.0.53")
    implementation("io.realm.kotlin:library-base:2.3.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    generateStubs = true
    arguments {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}
