package com.food.ordering.sytem.kotlin.order.service.domain.event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.sytem.kotlin.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderPaidEvent(
    override val order: Order,
    override val createdAt: ZonedDateTime,
    val orderPaidEventDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>
) : OrderEvent(order, createdAt)