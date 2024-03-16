package com.example.pokdex.data.models

data class Move(
    val move: com.example.pokdex.data.models.MoveX,
    val version_group_details: List<com.example.pokdex.data.models.VersionGroupDetail>
)