package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.multidex.android.pokemon.details.PokemonDetailsScreen
import dev.multidex.android.pokemon.list.PokemonListScreen

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            setContent {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemonList") {
                    composable("pokemonList") {
                        PokemonListScreen(navController = navController)
                    }
                    composable("pokemon/{pokemonId}") { entry ->
                        entry.arguments?.getInt("pokemonId")?.let { PokemonDetailsScreen(it) }
                            ?: throw IllegalStateException("Pokemon ID Not Available")
                    }
                }
            }
        }
    }
}
