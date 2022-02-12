package dev.multidex.pokemodel

import kotlinx.serialization.Serializable

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
            return when (json.type.name.lowercase()) {
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