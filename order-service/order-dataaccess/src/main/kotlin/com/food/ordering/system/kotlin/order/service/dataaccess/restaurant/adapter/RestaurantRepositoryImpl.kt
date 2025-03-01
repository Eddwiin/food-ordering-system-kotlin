package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.adapter

import com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.RestaurantRepository
import org.springframework.stereotype.Component

@Component
open class RestaurantRepositoryImpl(
    val restaurantJpaRepository: RestaurantJpaRepository,
    val restaurantDataAccessMapper: RestaurantDataAccessMapper
) : RestaurantRepository {

    override fun findRestaurantInformation(restaurant: Restaurant): Restaurant {
        val restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant)
        val restaurantEntities =
            restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.id!!.value!!, restaurantProducts)
        return restaurantDataAccessMapper.restaurantEntityToRestaurant(restaurantEntities)
    }
}