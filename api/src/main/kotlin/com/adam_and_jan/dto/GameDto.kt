package com.adam_and_jan.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameDto (
    val title: String,
    val description: String,
    val rating: Double,
    val gamePath: String,
    val ownerFk: Int
)