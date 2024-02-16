const val ApplicationVersion = "0.0.1"

object Versions {
    const val activityCompose = "1.8.2"
    const val androidCompileSdk = 34
    const val androidMinSdk = 23
    const val androidTargetSdk = androidCompileSdk
    const val androidxNavigation = "2.7.7"
    const val appCompat = "1.6.1"
    const val coil = "2.5.0"
    const val compose = "1.6.1"
    const val composeCompiler = "1.5.9"

    const val kmpNativeCoroutinesVersion = "0.11.1-new-mm"
    const val kotlinxSerialization = "1.6.2"
    const val ktor = "2.3.8"

    const val shadow = "8.1.1"
    const val sqlDelight = "2.0.1"

    const val gradleVersionsPlugin = "0.51.0"
}

object Deps {
    object Android {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.androidxNavigation}"
    }

    object Coil {
        const val core = "io.coil-kt:coil:${Versions.coil}"
        const val compose = "io.coil-kt:coil-compose:${Versions.coil}"
    }

    object Compose {
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val icons = "androidx.compose.material:material-icons-core:${Versions.compose}"
        const val iconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val test = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
    }

    object Gradle {
        const val shadow = "com.github.johnrengelman:shadow:${Versions.shadow}"
        const val sqlDelight = "app.cash.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
    }

    object Kotlinx {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val jsonSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val engineCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    }
}
