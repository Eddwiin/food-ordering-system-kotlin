package com.food.ordering.system.kotlin.restaurant.service.domain

import RestaurantDomainService
import com.food.ordering.system.kotlin.restaurant.service.domain.mapper.RestaurantDataMapper

class RestaurantApprovalRequestHelper(

    val restaurantDomainService: RestaurantDomainService? = null,
    val restaurantDataMapper: RestaurantDataMapper? = null,
    val restaurantRepository: RestaurantRepository? = null,
    val orderApprovalRepository: OrderApprovalRepository? = null,
    val orderApprovedMessagePublisher: OrderApprovedMessagePublisher? = null,
    val orderRejectedMessagePublisher: OrderRejectedMessagePublisher? = null,
) {
}