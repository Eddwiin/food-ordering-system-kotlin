package com.food.ordering.system.kotlin.dataaccess.restaurant.entity

import java.io.Serializable
import java.util.*

data class RestaurantEntityId(
    val restaurantId: UUID? = null,
    val productId: UUID? = null,
) : Serializable