package com.food.ordering.system.kotlin.dataaccess.restaurant.repository

import com.food.ordering.system.kotlin.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.kotlin.dataaccess.restaurant.entity.RestaurantEntityId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantJpaRepository : JpaRepository<RestaurantEntity, RestaurantEntityId> {
    fun findByRestaurantIdAndProductIdIn(
        restaurantId: UUID?,
        productIds: List<UUID?>?
    ): List<RestaurantEntity>?
}