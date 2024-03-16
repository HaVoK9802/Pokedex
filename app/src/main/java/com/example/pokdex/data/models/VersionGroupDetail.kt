package com.example.pokdex.data.models

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: com.example.pokdex.data.models.MoveLearnMethod,
    val version_group: com.example.pokdex.data.models.VersionGroup
)