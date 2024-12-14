package com.adam_and_jan.mappers

import com.adam_and_jan.dto.GameDto
import com.adam_and_jan.models.Game


object GameMapper {
    fun toDto(game: Game): GameDto {
        return GameDto(
            game.title,
            game.description,
            game.rating,
            game.gamePath,
            game.ownerFk
        )
    }
}