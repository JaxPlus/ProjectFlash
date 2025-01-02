package com.adam_and_jan.plugins.services

import com.adam_and_jan.models.ShopItem
import com.adam_and_jan.repository.ShopRepository
import com.adam_and_jan.repository.UserRepository

class ShopService(
    private val shopRepository: ShopRepository,
    private val userRepository: UserRepository,
) {
    suspend fun getAllShopItems(): List<ShopItem> =
        shopRepository.getAllShopItems()

    suspend fun getShopItem(itemId: Int): ShopItem =
        shopRepository.getShopItem(itemId)

    suspend fun buyShopItem(itemId: Int, email: String) {
        val item = getShopItem(itemId)
        val user = userRepository.getUserByEmail(email)

        if (user.money >= item.price && !user.inventory.contains(itemId)) {
            userRepository.setUserMoney(user.id, user.money - item.price)
            userRepository.addToUserInventory(user.id, item.id, user.inventory)
        }
        else {
            throw Exception("You don't have enough money to buy or you already have this item!")
        }
    }
}