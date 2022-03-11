package dev.multidex.pokemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    @SerialName("base_experience")
    val baseExperience: Int,
    val height: Int,
    @SerialName("is_default")
    val isDefault: Boolean,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val types: List<PokemonTypeJson>,
    val weight: Int
)

@Serializable
data class AbbreviatedPokemonResults(
    val results: List<AbbreviatedPokemon>
)

@Serializable
data class AbbreviatedPokemon(
    val name: String,
    val url: String
)