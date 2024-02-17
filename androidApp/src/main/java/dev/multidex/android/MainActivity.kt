package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dev.multidex.android.pokemon.details.PokemonDetailsScreen
import dev.multidex.android.pokemon.list.PokemonListScreen

class MainActivity : ComponentActivity(), ImageLoaderFactory {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "pokemonList") {
                composable("pokemonList") {
                    PokemonListScreen(navController = navController)
                }
                composable("pokemon/{pokemonId}") { entry ->
                    entry.arguments?.getString("pokemonId")
                        ?.let { PokemonDetailsScreen(it.toInt()) }
                        ?: throw IllegalStateException("Pokemon ID Not Available")
                }
            }
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}
