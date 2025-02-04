﻿package com.adam_and_jan.repository

import com.adam_and_jan.models.ShopItem
import java.sql.Connection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet

class ShopRepository(
    private val connection: Connection,
) {
    companion object {
        private const val SELECT_ALL_ITEMS = """SELECT * FROM items"""
        private const val SELECT_ITEM_BY_ID = """SELECT * FROM items WHERE id = ?"""
    }

    suspend fun getAllShopItems(): List<ShopItem> = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_ALL_ITEMS)
        val resultSet = statement.executeQuery()
        val items = mutableListOf<ShopItem>()

        while (resultSet.next()) {
            val shopItem = getItem(resultSet)

            items.add(shopItem)
        }

        return@withContext items
    }

    suspend fun getShopItem(itemId: Int): ShopItem = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_ITEM_BY_ID)
        statement.setInt(1, itemId)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val shopItem = getItem(resultSet)

            return@withContext shopItem
        }
        else {
            throw Exception("Item not found")
        }
    }

    private fun getItem(resultSet: ResultSet): ShopItem {
        val id = resultSet.getInt("id")
        val displayName = resultSet.getString("display_name")
        val name = resultSet.getString("name")
        val type = resultSet.getString("type")
        val desc = resultSet.getString("description")
        val price = resultSet.getInt("price")

        return ShopItem(id, displayName, name, type, desc, price)
    }
}