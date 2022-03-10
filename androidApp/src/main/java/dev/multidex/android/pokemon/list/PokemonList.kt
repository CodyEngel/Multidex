package dev.multidex.android.pokemon.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import dev.multidex.android.common.view.Chip
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.pokemon.PokemonRepository
import dev.multidex.pokemodel.Pokemon
import dev.multidex.pokemodel.PokemonType
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun PokemonListScreen(navController: NavController) {
    var state by remember { mutableStateOf(listOf<Pokemon>()) }
    LaunchedEffect("no-key") {
        launch {
            val pokemonRepository = PokemonRepository()
            val response = pokemonRepository.retrieve() as Response.Success<List<Pokemon>>
            state = response.result
        }
    }

    PokemonList(pokemons = state, navController = navController)
}

@Composable
fun PokemonList(pokemons: List<Pokemon>, navController: NavController) {
    LazyColumn {
        items(pokemons) { pokemon ->
            val types = pokemon.types.map { PokemonType.from(it) }
            PokedexItem(
                image = pokemon.sprites.other.officialArtwork.frontDefault,
                name = pokemon.name.replaceFirstChar { it.uppercase(Locale.US) },
                entry = pokemon.order,
                types = types,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokedexItem(
    image: String,
    name: String,
    entry: Int,
    types: List<PokemonType>,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(size = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 4.dp, RoundedCornerShape(size = 4.dp)),
        onClick = { navController.navigate("pokemon/123") }
    ) {
        Row {
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )
            Column {
                Text(text = name)
                Text(text = "#${entry}")
                Row {
                    types.forEach { Chip(text = it.text, colorHex = it.colorHex) }
                }
            }
        }
    }
}

@Preview
@Composable
fun PokedexPreview() {
    PokedexItem(
        image = "https://www.example.com/image.jpg",
        name = "Bulbasaur",
        entry = 1,
        types = listOf(PokemonType.GRASS),
        navController = rememberNavController()
    )
}