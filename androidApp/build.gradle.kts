plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    compileSdk = Versions.androidCompileSdk
    defaultConfig {
        testInstrumentationRunnerArguments += mapOf("clearPackageData" to "true")
        applicationId = "dev.multidex.android"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = 1
        versionName = ApplicationVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    testOptions {
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "dev.multidex.android"
}

dependencies {
    with(Deps.Android) {
        implementation(activityCompose)
        implementation(appCompat)
        implementation(navigation)
    }

    with(Deps.Coil) {
        implementation(core)
        implementation(compose)
    }

    with(Deps.Compose) {
        implementation(foundation)
        implementation(icons)
        implementation(iconsExtended)
        implementation(material)
        implementation(tooling)
        implementation(ui)

        androidTestImplementation(test)
    }

    with(Deps.Ktor) {
        implementation(clientCore)
        implementation(clientSerialization)
        implementation(engineCio)
    }

    implementation(project(":clientRepository"))
    implementation(project(":pokeModel"))
}
