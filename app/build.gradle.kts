plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.jetbrainsKotlin)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.googleHilt)
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
        multiDexEnabled = true

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
    lifecycleLibs()

    kotlinAndCoreKtx()

    navigation()

    implementation(Libs.appCompat)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    unitTest()

    hilt()

    room(withCompiler = true)

    //Test instrumental
    instrumentalTest()
}

kapt {
    correctErrorTypes = true
}
