package com.food.ordering.system.kotlin.restaurant.service.domain

import com.food.ordering.system.kotlin.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener
import org.springframework.stereotype.Service

@Service
class RestaurantApprovalRequestMessageListenerImpl : RestaurantApprovalRequestMessageListener {
    override fun approveOrder(restaurantApprovalRequest: RestaurantApprovalRequest?) {
        TODO("Not yet implemented")
    }
}