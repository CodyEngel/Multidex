package dev.multidex.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import io.ktor.client.request.*
import io.ktor.client.statement.*

class MainActivity : ComponentActivity() {
    private val client = HttpClient(CIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val response: HttpResponse = client.get("https://pokeapi.co/api/v2/pokemon")
            val responseText = response.readText()
            setContent {
                Text(text = responseText)
            }
        }
    }
}

enum class PokemonType(val text: String, val color: Int) {
    GRASS(text = "Grass", color = R.color.teal_700) // teal is close enough for now, will import more colors later
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
