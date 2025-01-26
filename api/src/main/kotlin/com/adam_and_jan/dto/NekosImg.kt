package com.adam_and_jan.dto

import kotlinx.serialization.Serializable

// temp
@Serializable
data class NekosImg(
    val id: Int,
    val url: String,
    val rating: String,
    val color_dominant: List<String>,
    val color_palette: List<List<String>>,
    val artist_name: String?,
    val tags: List<String>,
    val source_url: String?,
)
