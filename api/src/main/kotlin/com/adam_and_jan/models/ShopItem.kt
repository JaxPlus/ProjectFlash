package com.adam_and_jan.models

import kotlinx.serialization.Serializable

@Serializable
data class ShopItem(
    val id: Int,
    val displayName: String,
    val name: String,
    val type: String,
    val description: String,
    val price: Int,
)