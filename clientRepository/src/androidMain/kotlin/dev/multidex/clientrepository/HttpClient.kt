package dev.multidex.clientrepository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

private var CachedDefaultHttpClient: HttpClient? = null

actual val DefaultHttpClient: HttpClient
    get() {
        return CachedDefaultHttpClient ?: createClient().also { CachedDefaultHttpClient = it }
    }

private fun createClient(): HttpClient {
    return HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }
}