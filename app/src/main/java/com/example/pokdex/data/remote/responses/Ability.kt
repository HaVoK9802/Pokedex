package com.example.pokdex.data.remote.responses

data class Ability(
    val ability: com.example.pokdex.data.remote.responses.AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)