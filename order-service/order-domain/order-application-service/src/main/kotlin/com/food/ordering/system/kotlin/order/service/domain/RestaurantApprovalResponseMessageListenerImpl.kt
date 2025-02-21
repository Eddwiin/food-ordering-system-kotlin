package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class RestaurantApprovalResponseMessageListenerImpl : RestaurantApprovalResponseMessageListener {
    private val logger = KotlinLogging.logger {}

    override fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }

    override fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse) {
        TODO("Not yet implemented")
    }
}