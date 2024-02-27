package com.example.pokdex.data.remote.responses

data class Move(
    val move: com.example.pokdex.data.remote.responses.MoveX,
    val version_group_details: List<com.example.pokdex.data.remote.responses.VersionGroupDetail>
)