package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.repository

import com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.entity.RestaurantEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantJpaRepository : JpaRepository<RestaurantEntity, UUID> {
    fun findByRestaurantIdAndProductIdIn(restaurantId: UUID, productIds: List<UUID>): List<RestaurantEntity>
}