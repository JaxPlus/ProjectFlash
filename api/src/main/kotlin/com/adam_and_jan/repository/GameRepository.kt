package com.adam_and_jan.repository

import com.adam_and_jan.dto.GameDto
import com.adam_and_jan.mappers.GameMapper
import com.adam_and_jan.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection

class GameRepository(
    private val connection: Connection
) {
    companion object {
        private const val CREATE_GAME = """INSERT INTO games (title, description, rating, gamePath, owner_fk) VALUES (?, ?, ?, ?, ?)"""
        private const val SELECT_GAME_BY_ID = """SELECT * FROM games WHERE id = ?"""
    }

    suspend fun create(game: Game): Boolean = withContext(Dispatchers.IO) {

        val statement = connection.prepareStatement(CREATE_GAME)
        statement.setString(1, game.title)
        statement.setString(2, game.description)
        statement.setDouble(3, game.rating)
        statement.setString(4, game.gamePath)
        statement.setInt(5, game.ownerFk)

        return@withContext true
    }

    suspend fun getGame(id: Int): GameDto = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_GAME_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if(resultSet.next()) {
            val title = resultSet.getString("title")
            val description = resultSet.getString("description")
            val rating = resultSet.getDouble("rating")
            val gamePath = resultSet.getString("gamePath")
            val ownerFk = resultSet.getInt("owner_fk")

            return@withContext GameMapper.toDto(Game(title, description, rating, gamePath, ownerFk))
        }

        throw Exception("Game not found")


    }
}