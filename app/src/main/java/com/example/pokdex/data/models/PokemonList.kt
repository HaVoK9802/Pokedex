package com.example.pokdex.data.models

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<com.example.pokdex.data.models.Result>
)