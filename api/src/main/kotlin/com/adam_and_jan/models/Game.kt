package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Double,
    val gamePath: String,
    val ownerFk: Int
)