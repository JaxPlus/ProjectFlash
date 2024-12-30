package com.adam_and_jan.repository

import com.adam_and_jan.models.ShopItem
import java.sql.Connection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShopRepository(
    private val connection: Connection,
) {
    companion object {
        private const val SELECT_ALL_ITEMS = """SELECT * FROM items"""
    }

    suspend fun getAllShopItems(): List<ShopItem> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_ALL_ITEMS)
        val resultSet = statement.executeQuery()
        val items = mutableListOf<ShopItem>()

        while (resultSet.next()) {
            val displayName = resultSet.getString("display_name")
            val name = resultSet.getString("name")
            val type = resultSet.getString("type")

            items.add(ShopItem(displayName, name, type))
        }

        return@withContext items
    }
}