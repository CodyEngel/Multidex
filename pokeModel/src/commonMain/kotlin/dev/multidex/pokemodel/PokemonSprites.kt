package dev.multidex.pokemodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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