package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val response = client.downloadPokemon()
            setContent {
                LazyColumn {
                    items(response) { pokemon ->
                        PokedexItem(
                            image = R.drawable.ic_launcher_foreground,
                            name = pokemon.name,
                            entry = pokemon.order,
                            types = listOf(PokemonType.GRASS)
                        )
                    }
                }
            }
        }
    }
}

private suspend fun HttpClient.downloadPokemon(): List<Pokemon> {
    // TODO: add logging
    val pokemonToRetrieve =
        get<AbbreviatedPokemonResults>(urlString = "https://pokeapi.co/api/v2/pokemon")
    val requests = pokemonToRetrieve.results.map {
        coroutineScope {
            async {
                get<Pokemon>(
                    it.url
                )
            }
        }
    }

    return requests.map { it.await() }
        .sortedBy { it.order }
}

@Serializable
data class AbbreviatedPokemonResults(
    val results: List<AbbreviatedPokemon>
)

@Serializable
data class AbbreviatedPokemon(
    val name: String,
    val url: String
)

@Serializable
data class Pokemon(
    @SerialName("base_experience")
    val baseExperience: Int,
    val height: Int,
    @SerialName("is_default")
    val isDefault: Boolean,
    val name: String,
    val order: Int, // entry
    val sprites: Sprites,
    val weight: Int
)

fun Sprites.asList(): List<Sprite> {
    val officialArtWorkFrontDefault =
        Sprite.Other.OfficialArtwork.FrontDefault(other.officialArtwork.frontDefault)
    return listOf(officialArtWorkFrontDefault)
}

@Serializable
data class Sprites(
    @SerialName("back_default")
    val backDefault: String,
    @SerialName("front_default")
    val frontDefault: String,
    val other: SpriteOtherJson,
)

@Serializable
data class SpriteOtherJson(
    @SerialName("official-artwork")
    val officialArtwork: SpriteOtherOfficialArtwork
)

@Serializable
data class SpriteOtherOfficialArtwork(
    @SerialName("front_default")
    val frontDefault: String
)

sealed class Sprite {
    abstract val url: String

    sealed class Other : Sprite() {

        sealed class OfficialArtwork : Other() {
            data class FrontDefault(override val url: String) : Other()
        }
    }
}

enum class PokemonType(val text: String, val color: Int) {
    GRASS(
        text = "Grass",
        color = R.color.teal_700
    ) // teal is close enough for now, will import more colors later
}

@Composable
fun PokedexItem(
    image: Int,
    name: String,
    entry: Int,
    types: List<PokemonType>
) {
    Card {
        Row {
            Image(
                painter = painterResource(id = image),
                contentDescription = null
            )
            Column {
                Text(text = name)
                Text(text = "#${entry}")
                Row {
                    types.forEach { Chip(text = it.text, color = it.color) }
                }
            }
        }
    }
}

@Composable
fun Chip(text: String, color: Int) {
    Surface(
        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(4.dp),
        color = colorResource(color)
    ) {
        Text(text = text)
    }
}

@Preview
@Composable
fun PokedexPreview() {
    PokedexItem(
        image = R.drawable.ic_launcher_foreground,
        name = "Bulbasaur",
        entry = 1,
        types = listOf(PokemonType.GRASS)
    )
}

@Preview
@Composable
fun ChipPreview() {
    Chip(text = "Grass", color = R.color.teal_700)
}
