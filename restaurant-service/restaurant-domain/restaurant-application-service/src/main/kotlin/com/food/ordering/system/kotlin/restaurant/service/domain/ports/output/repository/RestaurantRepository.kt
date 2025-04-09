package com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository

import entity.Restaurant

interface RestaurantRepository {
    fun findRestaurantInformation(restaurant: Restaurant): Restaurant?
}