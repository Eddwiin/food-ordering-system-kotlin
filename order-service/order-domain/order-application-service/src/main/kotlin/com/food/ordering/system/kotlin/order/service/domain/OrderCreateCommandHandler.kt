package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class OrderCreateCommandHandler(
    val orderDataMapper: OrderDataMapper,
    val orderCreateHelper: OrderCreateHelper,
    val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent: OrderCreatedEvent =
            orderCreateHelper.persistOrder(createOrderCommand);
        logger.info { "Order is created with id ${orderCreatedEvent.order.id!!.value}" }
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent)
        return orderDataMapper.orderToCreateToCreateOrderResponse(
            orderCreatedEvent.order,
            "Order Created with Sucessfull"
        )
    }
}
