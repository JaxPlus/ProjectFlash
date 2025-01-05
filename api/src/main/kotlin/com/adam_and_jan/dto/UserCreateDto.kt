package com.adam_and_jan.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserCreateDto(
    val username: String,
    val email: String,
    val password: String,
)