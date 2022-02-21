package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.multidex.android.pokemon.list.PokemonList
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.pokemon.PokemonRepository
import dev.multidex.pokemodel.Pokemon

class MainActivity : ComponentActivity() {

    private val pokemonRepository = PokemonRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val response = pokemonRepository.retrieve() as Response.Success<List<Pokemon>>
            setContent {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemonList") {
                    composable("pokemonList") {
                        PokemonList(pokemons = response.result, navController = navController)
                    }
                    composable("pokemon/{pokemonId}") { entry ->
                        Text(text = "Hello, ${entry.arguments?.get("pokemonId")}")
                    }
                }
            }
        }
    }
}
