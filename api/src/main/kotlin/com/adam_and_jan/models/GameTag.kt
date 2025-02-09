package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class GameTag (
    val id: Int,
    val game_id: Int,
    val tag_id: Int
)