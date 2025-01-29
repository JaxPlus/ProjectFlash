package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val money: Int,
    val ranking_points: Int,
    val inventory: List<Int>,
)