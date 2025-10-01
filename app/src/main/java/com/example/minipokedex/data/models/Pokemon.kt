package com.example.minipokedex.data.models

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    val url: String
) {
    val id: Int
        get() = url.split("/").filter { it.isNotEmpty() }.last().toIntOrNull() ?: 0

    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
}

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val sprites: Sprites
) {
    val heightInMeters: Double get() = height / 10.0
    val weightInKg: Double get() = weight / 10.0
}

data class PokemonType(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String,
    val url: String
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int,
    val effort: Int,
    val stat: Stat
)

data class Stat(
    val name: String,
    val url: String
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String?
)