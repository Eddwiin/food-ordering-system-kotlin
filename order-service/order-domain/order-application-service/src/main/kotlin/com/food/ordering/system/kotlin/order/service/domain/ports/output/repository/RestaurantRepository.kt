package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RestaurantRepository {
    fun findRestaurantInformation(restaurant: Restaurant): Optional<Restaurant>
}