package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.entity

import java.util.*

class RestaurantEntityId(
    val restaurantId: UUID,
    val productId: UUID
) {
    companion object {
        fun builder() = Builder()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RestaurantEntityId) return false

        if (restaurantId != other.restaurantId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(restaurantId, productId)
    }

    data class Builder(
        var restaurantId: UUID? = null,
        var productId: UUID? = null
    ) {
        fun restaurantId(restaurantId: UUID) = apply { this.restaurantId = restaurantId }
        fun productId(productId: UUID) = apply { this.productId = productId }
        fun build(): RestaurantEntityId {
            if (restaurantId == null || productId == null) {
                throw IllegalStateException("Both restaurantId and productId must be provided")
            }
            return RestaurantEntityId(
                restaurantId = restaurantId!!,
                productId = productId!!
            )
        }
    }
}