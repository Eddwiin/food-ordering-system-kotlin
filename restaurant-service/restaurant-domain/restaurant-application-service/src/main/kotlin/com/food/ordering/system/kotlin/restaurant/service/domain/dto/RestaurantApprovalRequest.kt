package com.food.ordering.system.kotlin.restaurant.service.domain.dto

import com.food.ordering.system.kotlin.domain.valueobject.RestaurantOrderStatus
import entity.Product
import java.math.BigDecimal
import java.time.Instant


class RestaurantApprovalRequest(
    val id: String? = null,
    val sagaId: String? = null,
    val restaurantId: String? = null,
    val orderId: String? = null,
    val restaurantOrderStatus: RestaurantOrderStatus? = null,
    val products: List<Product>? = null,
    val price: BigDecimal? = null,
    val createdAt: Instant? = null
)