package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.mapper

import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.ProductId
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException
import com.food.ordering.system.kotlin.order.service.domain.entity.Product
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataAccessMapper {
    fun restaurantToRestaurantProducts(restaurant: Restaurant): List<UUID> {
        return restaurant.products.map { it.id!!.value!! }
    }

    fun restaurantEntityToRestaurant(restaurantEntities: List<RestaurantEntity>): Restaurant {

        val restaurantEntity = restaurantEntities.firstOrNull()
            ?: throw RestaurantDataAccessException("Restaurant could not be found!")

        val restaurantProducts = restaurantEntities.map {
            Product(
                ProductId(it.productId!!),
                it.productName,
                Money(it.productPrice!!)
            )
        }

        return Restaurant.builder()
            .restaurantId(RestaurantId(restaurantEntity.restaurantId!!))
            .products(restaurantProducts)
            .active(restaurantEntity.restaurantActive!!)
            .build()
    }
}