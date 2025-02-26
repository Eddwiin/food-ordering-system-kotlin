package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@IdClass(RestaurantEntityId::class)
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
class RestaurantEntity(
    @Id val restaurantId: UUID? = null,
    @Id val productId: UUID? = null,
    val restaurantName: String? = null,
    val restaurantActive: Boolean? = null,
    val productName: String? = null,
    val productPrice: BigDecimal? = null,
) {

    companion object {
        fun builder() = Builder()
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as RestaurantEntity

        if (restaurantId != other.restaurantId) return false
        if (productId != other.productId) return false
        if (restaurantName != other.restaurantName) return false
        if (restaurantActive != other.restaurantActive) return false
        if (productName != other.productName) return false
        if (productPrice != other.productPrice) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(
            restaurantId,
            productId,
            restaurantName,
            restaurantActive,
            productName,
            productPrice
        )
    }

    data class Builder(
        var restaurantId: UUID? = null,
        var productId: UUID? = null,
        var restaurantName: String? = null,
        var restaurantActive: Boolean? = null,
        var productName: String? = null,
        var productPrice: BigDecimal? = null
    ) {
        fun restaurantId(restaurantId: UUID) = apply { this.restaurantId = restaurantId }
        fun productId(productId: UUID) = apply { this.productId = productId }
        fun restaurantName(restaurantName: String) = apply { this.restaurantName = restaurantName }
        fun restaurantActive(restaurantActive: Boolean) = apply { this.restaurantActive = restaurantActive }
        fun productName(productName: String) = apply { this.productName = productName }
        fun productPrice(productPrice: BigDecimal) = apply { this.productPrice = productPrice }

        fun build(): RestaurantEntity {
            return RestaurantEntity(
                restaurantId = restaurantId,
                productId = productId,
                restaurantName = restaurantName,
                restaurantActive = restaurantActive,
                productName = productName,
                productPrice = productPrice
            )
        }
    }
}