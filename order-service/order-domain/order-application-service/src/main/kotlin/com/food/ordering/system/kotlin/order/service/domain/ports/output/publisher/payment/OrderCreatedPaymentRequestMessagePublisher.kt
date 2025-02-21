package com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher

interface OrderCreatedPaymentRequestMessagePublisher :
    DomainEventPublisher<com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent> {
}