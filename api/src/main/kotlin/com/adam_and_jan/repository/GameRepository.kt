package com.adam_and_jan.repository

import com.adam_and_jan.dto.GameDto
import com.adam_and_jan.mappers.GameMapper
import com.adam_and_jan.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet

class GameRepository(
    private val connection: Connection
) {
    companion object {
        private const val CREATE_GAME = """INSERT INTO games (title, description, rating, gamePath, owner_fk) VALUES (?, ?, ?, ?, ?)"""
        private const val SELECT_GAME_BY_ID = """SELECT * FROM games WHERE id = ?"""
        private const val SELECT_ALL_GAMES = """SELECT * FROM games"""
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


    suspend fun getAllGames(): List<Game> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_ALL_GAMES)
        val resultSet = statement.executeQuery()
        val games = mutableListOf<Game>()

        while (resultSet.next()) {
            val game = getGame(resultSet)
            games.add(game)
        }

        return@withContext games
    }


    suspend fun getGame(id: Int): GameDto = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_GAME_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if(resultSet.next()) {
            val game = getGame(resultSet)

            return@withContext GameMapper.toDto(game)
        }

        throw Exception("Game not found")
    }

    private fun getGame(resultSet: ResultSet): Game {
        val id = resultSet.getInt("id")
        val title = resultSet.getString("title")
        val description = resultSet.getString("description")
        val rating = resultSet.getDouble("rating")
        val gamePath = resultSet.getString("gamePath")
        val ownerFk = resultSet.getInt("owner_fk")

        return Game(id, title, description, rating, gamePath, ownerFk)
    }
}