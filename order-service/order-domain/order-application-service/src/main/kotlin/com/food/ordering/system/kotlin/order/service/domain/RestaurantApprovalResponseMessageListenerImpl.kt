package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
open class RestaurantApprovalResponseMessageListenerImpl(
    val orderApprovalSaga: OrderApprovalSaga
) : RestaurantApprovalResponseMessageListener {
    private val logger = KotlinLogging.logger {}

    override fun orderApproved(restaurantApprovalResponse: RestaurantApprovalResponse) {
        orderApprovalSaga.process(restaurantApprovalResponse)
        logger.info { "Order is approved with id: ${restaurantApprovalResponse.orderId}" }
    }

    override fun orderRejected(restaurantApprovalResponse: RestaurantApprovalResponse) {
        val domainEvent = orderApprovalSaga.rollback(restaurantApprovalResponse)
        logger.info {
            "Publishing order cancelled event for order id: ${restaurantApprovalResponse.orderId} with failure" +
                    "message: ${restaurantApprovalResponse.failureMessages.joinToString { it }}"
        }
        domainEvent.fire()
    }
}