package com.food.ordering.sytem.kotlin.order.service.domain

import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.sytem.kotlin.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
open class OrderCreatedEventApplicationListener(
    val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {
    @TransactionalEventListener
    open fun process(orderCreatedEvent: OrderCreatedEvent) {

    }
}