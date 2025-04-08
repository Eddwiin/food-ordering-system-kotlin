package com.food.ordering.system.kotlin.order.service.domain.event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderCreatedEvent(
    override val order: Order,
    override val createdAt: ZonedDateTime,
    orderCreatedEventPublisher: DomainEventPublisher<OrderCreatedEvent>
) : OrderEvent(order, createdAt) {
    override fun fire() {
        TODO("Not yet implemented")
    }
}