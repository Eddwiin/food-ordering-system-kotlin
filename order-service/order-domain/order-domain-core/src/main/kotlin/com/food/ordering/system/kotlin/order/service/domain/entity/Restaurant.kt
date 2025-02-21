package com.food.ordering.system.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId

class Restaurant private constructor(
    val products: List<Product>,
    val active: Boolean
) : AggregateRoot<RestaurantId>() {

    companion object {
        fun builder() = Builder()
    }

    init {
        this.id = builder().restaurantId
    }

    class Builder {
        var restaurantId: RestaurantId? = null
        var products: List<Product> = emptyList()
        var active: Boolean = false

        fun restaurantId(id: RestaurantId) = apply { this.restaurantId = id }
        fun products(products: List<Product>) = apply { this.products = products }
        fun active(active: Boolean) = apply { this.active = active }

        fun build(): Restaurant {
            val restaurant = Restaurant(products, active)
            restaurant.id = restaurantId
            return restaurant
        }
    }
}