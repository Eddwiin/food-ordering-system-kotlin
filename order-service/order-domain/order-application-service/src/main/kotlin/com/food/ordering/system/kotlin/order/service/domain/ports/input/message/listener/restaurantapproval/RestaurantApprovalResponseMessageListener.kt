package com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.restaurantapproval

import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse

interface RestaurantApprovalResponseMessageListener {
    fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse)
    fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse)
}