package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
open class OrderCreateCommandHandler(
    val orderDataMapper: OrderDataMapper,
    val orderCreateHelper: OrderCreateHelper,
    val paymentOutboxHelper: PaymentOutboxHelper,
    private val orderSagaHelper: OrderSagaHelper,
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun createOrder(createOrderCommand: CreateOrderCommand): CreateOrderResponse {
        val orderCreatedEvent: OrderCreatedEvent =
            orderCreateHelper.persistOrder(createOrderCommand)
        logger.info { "Order is created with id ${orderCreatedEvent.order.id!!.value}" }
        val createOrderResponse = orderDataMapper.orderToCreateToCreateOrderResponse(
            orderCreatedEvent.order,
            "Order Created with Sucessfull"
        )

        paymentOutboxHelper.savePaymentOutboxMessage(
            orderDataMapper.orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent),
            orderCreatedEvent.order.orderStatus!!,
            orderSagaHelper.orderStatusToSagaStatus(orderCreatedEvent.order.orderStatus!!),
            OutboxStatus.STARTED,
            UUID.randomUUID()
        )

        logger.info { "Returning CreateOrderResponse with order id ${orderCreatedEvent.order.id!!.value}" }
        return createOrderResponse
    }
}
