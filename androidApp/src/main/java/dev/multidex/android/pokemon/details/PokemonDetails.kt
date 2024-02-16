package dev.multidex.android.pokemon.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.multidex.android.common.view.Chip
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.pokemon.PokemonDetailsRepository
import dev.multidex.clientrepository.pokemon.RetrievePokemonDetailsQuery
import dev.multidex.pokemodel.Pokemon
import dev.multidex.pokemodel.PokemonType

@Composable
fun PokemonDetailsScreen(id: Int) {
    var state by remember { mutableStateOf<PokemonDetailsState>(PokemonDetailsState.Loading) }
    LaunchedEffect(key1 = id) {
        val pokemonDetailsRepository = PokemonDetailsRepository()
        val response =
            pokemonDetailsRepository.retrieve(RetrievePokemonDetailsQuery(id)) as Response.Success<Pokemon>
        state = PokemonDetailsState.Ready(response.result)
    }

    PokemonDetails(state)
}

@Composable
fun PokemonDetails(state: PokemonDetailsState) {
    when (state) {
        is PokemonDetailsState.Loading -> CircularProgressIndicator()
        is PokemonDetailsState.Ready -> PokemonDetailsReady(pokemon = state.pokemon)
    }
}

@Composable
private fun PokemonDetailsReady(pokemon: Pokemon) {
    val image = pokemon.sprites.other.officialArtwork.frontDefault
    val types = pokemon.types.map { PokemonType.from(it) }
    Row {
        Image(
            painter = rememberAsyncImagePainter(model = image),
            contentDescription = null,
            modifier = Modifier.size(128.dp)
        )
        Column {
            Text(text = pokemon.name)
            Text(text = "#${pokemon.id}")
            Row {
                types.forEach { Chip(text = it.text, colorHex = it.colorHex) }
            }
        }
    }
}

sealed class PokemonDetailsState {
    data object Loading : PokemonDetailsState()

    data class Ready(val pokemon: Pokemon) : PokemonDetailsState()
}