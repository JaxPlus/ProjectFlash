package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class Tag (
    val id: Int,
    val tagName: String
)