package com.adam_and_jan.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameRequest(
    val editUsername: String
)
