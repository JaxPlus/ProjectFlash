package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val game_path: String,
    val owner_fk: Int
)