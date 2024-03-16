package com.example.pokdex.data.models

data class Pokemon(
    val abilities: List<com.example.pokdex.data.models.Ability>,
    val base_experience: Int,
    val cries: com.example.pokdex.data.models.Cries,
    val forms: List<com.example.pokdex.data.models.Form>,
    val game_indices: List<com.example.pokdex.data.models.GameIndice>,
    val height: Int,
    val held_items: List<Any>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<com.example.pokdex.data.models.Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<Any>,
    val past_types: List<Any>,
    val species: com.example.pokdex.data.models.Species,
    val sprites: com.example.pokdex.data.models.Sprites,
    val stats: List<com.example.pokdex.data.models.Stat>,
    val types: List<com.example.pokdex.data.models.Type>,
    val weight: Int
)