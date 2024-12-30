package com.adam_and_jan.plugins.services

import com.adam_and_jan.models.ShopItem
import com.adam_and_jan.repository.ShopRepository

class ShopService(
    private val shopRepository: ShopRepository,
) {
    suspend fun getAllShopItems(): List<ShopItem> =
        shopRepository.getAllShopItems()
}