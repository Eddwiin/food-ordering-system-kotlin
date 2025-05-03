package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga.SagaStep
import com.food.ordering.system.kotlin.domain.event.EmptyEvent
import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment.OrderCancelledPaymentRequestMessagePublisher
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class OrderApprovalSaga(
    val orderDomainService: OrderDomainService,
    val orderSagaHelper: OrderSagaHelper,
    val orderCancelledPaymentRequestMessagePublisher: OrderCancelledPaymentRequestMessagePublisher
) : SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun process(restaurantApprovalResponse: RestaurantApprovalResponse): EmptyEvent {
        logger.info { "Approving order with id: ${restaurantApprovalResponse.restaurantId}" }
        val order = orderSagaHelper.findOrder(restaurantApprovalResponse.orderId)
        orderDomainService.approvedOrder(order)
        orderSagaHelper.saveOrder(order)
        logger.info { "Order with id: ${order.id!!.value} is approved successfully!" }
        return EmptyEvent.instance
    }

    @Transactional
    override fun rollback(restaurantApprovalResponse: RestaurantApprovalResponse): OrderCancelledEvent {
        logger.info { "Cancelling order with id: ${restaurantApprovalResponse.restaurantId}" }
        val order = orderSagaHelper.findOrder(restaurantApprovalResponse.orderId)
        orderDomainService.cancelOrder(order, restaurantApprovalResponse.failureMessages)
        orderSagaHelper.saveOrder(order)
        logger.info { "Order with id: ${order.id!!.value} is cancelled successfully!" }
        TODO("Not yet implemented")
    }
}