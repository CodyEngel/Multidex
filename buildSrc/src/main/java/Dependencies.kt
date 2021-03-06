const val ApplicationVersion = "0.0.1"

object Versions {
    const val activityCompose = "1.4.0-beta01"
    const val androidCompileSdk = 31
    const val androidMinSdk = 23
    const val androidTargetSdk = androidCompileSdk
    const val androidxNavigation = "2.4.1"
    const val appCompat = "1.4.1"
    const val coil = "1.4.0"
    const val compose = "1.1.0-rc01"
    const val composeCompiler = "1.1.0-rc02"

    const val kmpNativeCoroutinesVersion = "0.11.1-new-mm"
    const val kotlinxSerialization = "1.3.2"
    const val ktor = "1.6.7"

    const val shadow = "7.0.0"
    const val sqlDelight = "1.5.3"

    const val gradleVersionsPlugin = "0.39.0"
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
        const val shadow = "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:${Versions.shadow}"
        const val sqlDelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
    }

    object Kotlinx {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientLogging ="io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val engineCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    }
}
