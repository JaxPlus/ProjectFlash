package com.adam_and_jan.repository

import com.adam_and_jan.dto.GameDto
import com.adam_and_jan.mappers.GameMapper
import com.adam_and_jan.models.Game
import com.adam_and_jan.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

class GameRepository(
    private val connection: Connection,
    private val client: SupabaseClient
) {
    companion object {
        //private const val CREATE_GAME = """INSERT INTO games (title, description, rating, gamePath, owner_fk) VALUES (?, ?, ?, ?, ?)"""
        //private const val SELECT_GAME_BY_ID = """SELECT * FROM games WHERE id = ?"""
        //private const val SELECT_ALL_GAMES = """SELECT * FROM games"""

        //private const val SELECT_ALL_TAGS = """SELECT * FROM tags"""
        //private const val SELECT_GAMES_BY_TAG = """SELECT * FROM games where id IN(SELECT game_id FROM games_tags WHERE tag_id = ?)"""
    }

//    suspend fun create(game: Game): Boolean = withContext(Dispatchers.IO) {
//
//        val statement = connection.prepareStatement(CREATE_GAME)
//        statement.setString(1, game.title)
//        statement.setString(2, game.description)
//        statement.setDouble(3, game.rating)
//        statement.setString(4, game.gamePath)
//        statement.setInt(5, game.ownerFk)
//
//        return@withContext true
//    }

    suspend fun create(game: Game): Boolean {
        client.postgrest["games"]
            .insert(game)

        return true
    }


//    suspend fun getAllGames(): List<Game> = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_ALL_GAMES)
//        val resultSet = statement.executeQuery()
//        val games = mutableListOf<Game>()
//
//        while (resultSet.next()) {
//            val game = getGame(resultSet)
//            games.add(game)
//        }
//
//        return@withContext games
//    }

    suspend fun getAllGames(): List<Game> {
        val query = client.postgrest["games"]
            .select()

        return query.decodeList<Game>()
    }


//    suspend fun getGame(id: Int): GameDto = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_GAME_BY_ID)
//        statement.setInt(1, id)
//        val resultSet = statement.executeQuery()
//
//        if(resultSet.next()) {
//            val game = getGame(resultSet)
//
//            return@withContext GameMapper.toDto(game)
//        }
//
//        throw Exception("Game not found")
//    }

    suspend fun getGame(id: Int): GameDto {
        val query = client.postgrest["games"]
            .select() {
                filter {
                    eq("id", id)
                }
            }

        val game = query.decodeSingle<Game>()
        return GameMapper.toDto(game)
    }

//    suspend fun getAllTags(): List<Tag> = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_ALL_TAGS)
//        val resultSet = statement.executeQuery()
//        val tags = mutableListOf<Tag>()
//
//        while (resultSet.next()) {
//            val tag = getTag(resultSet)
//            tags.add(tag)
//        }
//
//        return@withContext tags
//    }

    suspend fun getAllTags(): List<Tag> {
        val query = client.postgrest["tags"]
            .select()
        return query.decodeList<Tag>()
    }

//    suspend fun getAllGamesByTag(id: Int): List<Game> = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_GAMES_BY_TAG)
//        statement.setInt(1, id)
//        val resultSet = statement.executeQuery()
//        val games = mutableListOf<Game>()
//
//        while (resultSet.next()) {
//            val game = getGame(resultSet)
//            games.add(game)
//        }
//
//        return@withContext games
//    }

    suspend fun getAllGamesByTag(id: Int): List<Game> {

        val subquery = client.postgrest["games_tags"]
            .select(columns = Columns.list("game_id")) {
                filter {
                    eq("tag_id", id)
                }
            }

        val gameId = subquery.decodeSingle<Int>()

        val query = client.postgrest["games"]
            .select() {
                filter {
                    eq("id", gameId)
                }
            }

        return query.decodeList<Game>()
    }

//    private fun getGame(resultSet: ResultSet): Game {
//        val id = resultSet.getInt("id")
//        val title = resultSet.getString("title")
//        val description = resultSet.getString("description")
//        val rating = resultSet.getDouble("rating")
//        val gamePath = resultSet.getString("gamePath")
//        val ownerFk = resultSet.getInt("owner_fk")
//
//        return Game(id, title, description, rating, gamePath, ownerFk)
//    }
//
//    private fun getTag(resultSet: ResultSet): Tag {
//        val id = resultSet.getInt("id")
//        val tagName = resultSet.getString("tag_name")
//
//        return Tag(id, tagName)
//    }
}