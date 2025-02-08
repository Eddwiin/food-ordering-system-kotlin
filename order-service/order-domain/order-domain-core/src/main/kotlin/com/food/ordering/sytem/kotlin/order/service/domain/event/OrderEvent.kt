package com.food.ordering.sytem.kotlin.order.service.domain.event

import com.food.ordering.system.kotlin.domain.event.DomainEvent
import com.food.ordering.sytem.kotlin.order.service.domain.entity.Order
import java.time.ZonedDateTime

abstract class OrderEvent(
    open val order: Order,
    open val createdAt: ZonedDateTime
) : DomainEvent<Order>