package com.food.ordering.system.kotlin.restaurant.service.dataaccess.mapper

import com.food.ordering.system.kotlin.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.kotlin.dataaccess.restaurant.exception.RestaurantDataAccessException
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.ProductId
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import entity.OrderDetail
import entity.Product
import entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataAccessMapper {

    fun restaurantToRestaurantProducts(restaurant: Restaurant): List<UUID?>? {
        return restaurant.orderDetail?.products?.map { it.id?.value }
    }

    fun restaurantEntityToRestaurant(restaurantEntities: List<RestaurantEntity>): Restaurant {
        val restaurantEntity = restaurantEntities.firstOrNull()
            ?: throw RestaurantDataAccessException("No restaurants found!")

        val restaurantProducts = restaurantEntities.map { entity ->
            Product(
                productId = ProductId(entity.productId!!),
                name = entity.productName,
                price = Money(entity.productPrice!!),
                available = entity.productAvailable!!
            )
        }

        return Restaurant(
            restaurantId = RestaurantId(restaurantEntity.restaurantId!!),
            orderDetail = OrderDetail(
                products = restaurantProducts
            ),
            active = restaurantEntity.restaurantActive!!
        )
    }
}