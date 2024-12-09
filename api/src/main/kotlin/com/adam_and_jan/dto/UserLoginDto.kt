package com.adam_and_jan.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDto(
    val email: String,
    val password: String,
)
