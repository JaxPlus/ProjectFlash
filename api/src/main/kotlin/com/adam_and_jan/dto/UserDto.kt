package com.adam_and_jan.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val username: String,
    val email: String,
    val money: Int,
    val inventory: List<Int>,
)