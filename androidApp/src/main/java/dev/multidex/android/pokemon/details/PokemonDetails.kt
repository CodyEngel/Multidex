package dev.multidex.android.pokemon.details

import androidx.compose.runtime.Composable
import androidx.compose.material.Text

@Composable
fun PokemonDetailsScreen(id: Int) {
    PokemonDetails(id = id)
}

@Composable
fun PokemonDetails(id: Int) {
    Text(text = "Future Home Of Pokemon $id")
}