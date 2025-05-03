package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga.SagaStep
import com.food.ordering.system.kotlin.domain.event.EmptyEvent
import com.food.ordering.system.kotlin.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class OrderPaymentSaga(
    val orderDomainService: OrderDomainService,
    val orderSagaHelper: OrderSagaHelper,
    val orderPaidRestaurantRequestMessagePublisher: OrderPaidRestaurantRequestMessagePublisher,
) : SagaStep<PaymentResponse, OrderPaidEvent, EmptyEvent> {
    private val logger = KotlinLogging.logger { }

    @Transactional
    override fun process(paymentResponse: PaymentResponse): OrderPaidEvent {
        logger.info { "Completing payment for order with id: ${paymentResponse.orderId}" }
        val order = findOrder(paymentResponse.orderId)
        val domainEvent = orderDomainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher)
        orderSagaHelper.saveOrder(order);
        logger.info { "Order with id: ${order.id!!.value} is paid successfully!" }
        return domainEvent
    }

    @Transactional
    override fun rollback(paymentResponse: PaymentResponse): EmptyEvent {
        logger.info { "Cancelling order with id: ${paymentResponse.orderId}" }
        val order = findOrder(paymentResponse.orderId);
        orderDomainService.cancelOrderPayment(order, paymentResponse.failureMessages);
        orderSagaHelper.saveOrder(order);
        logger.info { "Order with id: ${order.id!!.value} is cancelled successfully!" }
        return EmptyEvent.instance;

    }

    private fun findOrder(orderId: String): Order {
        return orderSagaHelper.findOrder(orderId)
    }

}