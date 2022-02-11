package dev.multidex.android

import android.os.Bundle
import android.util.Log
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
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

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

private suspend fun HttpClient.downloadPokemon(): List<Pokemon> {
    val networkTag = "Network"
    val pokemonToRetrieveRequest = "https://pokeapi.co/api/v2/pokemon/?limit=10"
    Log.d(networkTag, "request: $pokemonToRetrieveRequest")
    val pokemonToRetrieve =
        get<AbbreviatedPokemonResults>(urlString = pokemonToRetrieveRequest)
    Log.d(networkTag, "result: $pokemonToRetrieve")
    val requests = pokemonToRetrieve.results.map { abbreviatedPokemon ->
        coroutineScope {
            async {
                Log.d(networkTag, "request: ${abbreviatedPokemon.url}")
                get<Pokemon>(
                    abbreviatedPokemon.url
                ).also { Log.d(networkTag, "result: $it") }
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
    val types: List<PokemonTypeJson>,
    val weight: Int
)

fun Sprites.asList(): List<Sprite> {
    val officialArtWorkFrontDefault =
        Sprite.Other.OfficialArtwork.FrontDefault(other.officialArtwork.frontDefault)
    return listOf(officialArtWorkFrontDefault)
}

@Serializable
data class PokemonTypeJson(
    val slot: Int,
    val type: PokemonTypeDetailsJson
)

@Serializable
data class PokemonTypeDetailsJson(
    val name: String,
    val url: String
)

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

enum class PokemonType(val text: String, val colorHex: String) {
    NORMAL(
        text = "Normal",
        colorHex = "#A8A77A"
    ),
    FIGHTING(
        text = "Fighting",
        colorHex = "#C22E28"
    ),
    FLYING(
        text = "Flying",
        colorHex = "#A98FF3"
    ),
    POISON(
        text = "Poison",
        colorHex = "#A33EA1"
    ),
    GROUND(
        text = "Ground",
        colorHex = "#E2BF65"
    ),
    ROCK(
        text = "Rock",
        colorHex = "#B6A136"
    ),
    BUG(
        text = "Bug",
        colorHex = "#A6B91A"
    ),
    GHOST(
        text = "Ghost",
        colorHex = "#735797"
    ),
    STEEL(
        text = "Normal",
        colorHex = "#B7B7CE"
    ),
    FIRE(
        text = "Fire",
        colorHex = "#EE8130"
    ),
    WATER(
        text = "Water",
        colorHex = "#6390F0"
    ),
    GRASS(
        text = "Grass",
        colorHex = "#7AC74C"
    ),
    ELECTRIC(
        text = "Electric",
        colorHex = "#F7D02C"
    ),
    PSYCHIC(
        text = "Psychic",
        colorHex = "#F95587"
    ),
    ICE(
        text = "Ice",
        colorHex = "#96D9D6"
    ),
    DRAGON(
        text = "Dragon",
        colorHex = "#6F35FC"
    ),
    DARK(
        text = "Dark",
        colorHex = "#705746"
    ),
    FAIRY(
        text = "Fairy",
        colorHex = "#D685AD"
    ),
    UNKNOWN(
        text = "Unknown",
        colorHex = "#000000"
    ),
    SHADOW(
        text = "Shadow",
        colorHex = "#222222"
    ),
    NOT_FOUND(
        text = "Not Found",
        colorHex = "#000000"
    );

    companion object {
        fun from(json: PokemonTypeJson): PokemonType {
            return when (json.type.name.lowercase(Locale.US)) {
                "normal" -> NORMAL
                "fighting" -> FIGHTING
                "flying" -> FLYING
                "poison" -> POISON
                "ground" -> GROUND
                "rock" -> ROCK
                "bug" -> BUG
                "ghost" -> GHOST
                "steel" -> STEEL
                "fire" -> FIRE
                "water" -> WATER
                "grass" -> GRASS
                "electric" -> ELECTRIC
                "psychic" -> PSYCHIC
                "ice" -> ICE
                "dragon" -> DRAGON
                "dark" -> DARK
                "fairy" -> FAIRY
                "unknown" -> UNKNOWN
                "shadow" -> SHADOW
                else -> NOT_FOUND
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
