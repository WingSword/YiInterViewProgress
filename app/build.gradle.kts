plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
    alias (libs.plugins.snapshort.sqldelight)
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

    implementation(libs.androidx.appcompat.resources)

    implementation(libs.androidx.fragment.ktx)

    implementation (libs.krealmextensions)

    // 对于 Single 和 Flowable 查询：
    implementation (libs.rxjava)
    implementation (libs.rxandroid)
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
