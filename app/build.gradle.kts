plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "wottrich.github.io.pomodorouniverse"
    compileSdk = AndroidSdk.targetSdk
    buildToolsVersion = AndroidSdk.buildToolsVersion

    defaultConfig {
        applicationId = "wottrich.github.io.pomodorouniverse"
        minSdk = AndroidSdk.minSdk
        targetSdk = AndroidSdk.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = AndroidSdk.javaVersion
        targetCompatibility = AndroidSdk.javaVersion
    }
    kotlinOptions {
        jvmTarget = AndroidSdk.javaVersion.toString()
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    kotlinAndCoreKtx()

    koin()
    navigation()

    implementation(Libs.appCompat)

    unitTest()

    //Test instrumental
    instrumentalTest()

//    implementation ("androidx.core:core-ktx:1.7.0")
//    implementation ("androidx.appcompat:appcompat:1.5.1")
//    implementation ("com.google.android.material:material:1.7.0")
//    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
//
//    //Dependencies added
//    implementation ("androidx.fragment:fragment-ktx:1.5.4")
//    //Dependencies added
//
//    testImplementation ("junit:junit:4.13.2")
//    androidTestImplementation ("androidx.test.ext:junit:1.1.4")
//    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
}