package com.food.ordering.system.kotlin.order.service.dataaccess.restaurant.exception

class RestaurantDataAccessException(override val message: String) : RuntimeException(message) {
}