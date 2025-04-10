package com.food.ordering.system.kotlin.dataaccess.restaurant.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@IdClass(RestaurantEntityId::class)
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
data class RestaurantEntity(
    @Id
    val restaurantId: UUID? = null,
    @Id
    val productId: UUID? = null,
    val restaurantName: String? = null,
    val restaurantActive: Boolean? = null,
    val productName: String? = null,
    val productPrice: BigDecimal? = null,
    val productAvailable: Boolean? = null,
)