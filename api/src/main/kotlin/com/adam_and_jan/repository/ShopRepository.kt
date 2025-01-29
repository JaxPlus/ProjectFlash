package com.adam_and_jan.repository

import com.adam_and_jan.models.ShopItem
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopRepository(
    private val client: SupabaseClient
) {

    suspend fun getAllShopItems(): List<ShopItem> = withContext(Dispatchers.IO) {
        val query = client.postgrest["items"]
            .select()

        return@withContext query.decodeList<ShopItem>()
    }

    suspend fun getShopItem(itemId: Int): ShopItem = withContext(Dispatchers.IO) {
        val query = client.postgrest["items"]
            .select() {
                filter {
                    eq("id", itemId)
                }
            }

        return@withContext query.decodeSingle<ShopItem>()
    }
}