package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.pokemon.PokemonRepository
import dev.multidex.pokemodel.Pokemon
import dev.multidex.pokemodel.PokemonType
import java.util.*

class MainActivity : ComponentActivity() {

    private val pokemonRepository = PokemonRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val response = pokemonRepository.retrieve() as Response.Success<List<Pokemon>>
            setContent {
                LazyColumn {
                    items(response.result) { pokemon ->
                        val types = pokemon.types.map { PokemonType.from(it) }
                        PokedexItem(
                            image = pokemon.sprites.other.officialArtwork.frontDefault,
                            name = pokemon.name.replaceFirstChar { it.uppercase(Locale.US) },
                            entry = pokemon.order,
                            types = types
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokedexItem(
    image: String,
    name: String,
    entry: Int,
    types: List<PokemonType>
) {
    Card(
        shape = RoundedCornerShape(size = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .shadow(elevation = 4.dp, RoundedCornerShape(size = 4.dp)),
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

@Composable
fun Chip(text: String, colorHex: String) {
    Surface(
        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp),
        color = Color(colorHex.toColorInt())
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text
        )
    }
}

@Preview
@Composable
fun PokedexPreview() {
    PokedexItem(
        image = "https://www.example.com/image.jpg",
        name = "Bulbasaur",
        entry = 1,
        types = listOf(PokemonType.GRASS)
    )
}

@Preview
@Composable
fun ChipPreview() {
    Chip(text = "Grass", colorHex = "#FF0000")
}
