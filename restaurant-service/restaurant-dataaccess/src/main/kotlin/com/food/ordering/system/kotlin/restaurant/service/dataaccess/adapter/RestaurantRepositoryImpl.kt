package com.food.ordering.system.kotlin.restaurant.service.dataaccess.adapter

import com.food.ordering.system.kotlin.dataaccess.restaurant.entity.RestaurantEntity
import com.food.ordering.system.kotlin.dataaccess.restaurant.repository.RestaurantJpaRepository
import com.food.ordering.system.kotlin.restaurant.service.dataaccess.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository.RestaurantRepository
import entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantRepositoryImpl(
    val restaurantJpaRepository: RestaurantJpaRepository,
    val restaurantDataAccessMapper: RestaurantDataAccessMapper
) : RestaurantRepository {
    override fun findRestaurantInformation(restaurant: Restaurant): Restaurant? {

        val restaurantProducts: List<UUID?>? =
            restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant)

        val restaurantEntities: List<RestaurantEntity>? =
            restaurantJpaRepository.findByRestaurantIdAndProductIdIn(
                restaurant.restaurantId.uuid,
                restaurantProducts
            )

        return restaurantEntities?.let { restaurantDataAccessMapper.restaurantEntityToRestaurant(it) }
    }

}