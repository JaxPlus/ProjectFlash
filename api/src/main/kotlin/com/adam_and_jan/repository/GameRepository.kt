package com.adam_and_jan.repository

import com.adam_and_jan.dto.GameDto
import com.adam_and_jan.mappers.GameMapper
import com.adam_and_jan.models.Game
import com.adam_and_jan.models.GameTag
import com.adam_and_jan.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class GameRepository(
    private val client: SupabaseClient
) {

    suspend fun create(game: Game): Boolean = withContext(Dispatchers.IO) {
        client.postgrest["games"]
            .insert(game)

        return@withContext true
    }

    suspend fun getAllGames(): List<Game> = withContext(Dispatchers.IO)  {
        val query = client.postgrest["games"]
            .select()

        return@withContext query.decodeList<Game>()
    }

    suspend fun getGame(id: Int): GameDto = withContext(Dispatchers.IO)  {
        val query = client.postgrest["games"]
            .select() {
                filter {
                    eq("id", id)
                }
            }

        val game = query.decodeSingle<Game>()
        return@withContext GameMapper.toDto(game)
    }

    suspend fun getAllTags(): List<Tag> = withContext(Dispatchers.IO)  {
        val query = client.postgrest
            .from("tags")
            .select()
        return@withContext query.decodeList<Tag>()
    }

    suspend fun getAllGamesByTag(id: Int): List<Game> = withContext(Dispatchers.IO)  {

        val subquery = client.postgrest["games_tags"]
            .select() {
                filter {
                    eq("tag_id", id)
                }
            }

        val gameTag = subquery.decodeSingle<GameTag>()

        val query = client.postgrest["games"]
            .select() {
                filter {
                    eq("id", gameTag.game_id)
                }
            }

        return@withContext query.decodeList<Game>()
    }
}