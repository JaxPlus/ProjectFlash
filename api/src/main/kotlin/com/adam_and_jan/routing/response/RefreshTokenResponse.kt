package com.adam_and_jan.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val token: String
)
