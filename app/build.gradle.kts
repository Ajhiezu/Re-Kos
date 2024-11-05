plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.jedu.re_kos"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.jedu.re_kos"
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

    buildFeatures {
        viewBinding {
            enable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation ("com.tbuonomo:dotsindicator:4.3")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.viewpager:viewpager:1.0.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}