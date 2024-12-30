package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val displayName: String,
    val name: String,
    val type: String,
)