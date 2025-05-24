package com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment

import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import org.apache.logging.log4j.util.BiConsumer

interface PaymentRequestMessagePublisher {
    fun publish(
        orderPaymentOutboxMessage: OrderPaymentOutboxMessage,
        outboxCallback: BiConsumer<OrderPaymentOutboxMessage, OutboxStatus>
    )
}