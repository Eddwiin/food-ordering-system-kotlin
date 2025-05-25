package com.food.ordering.system.kotlin.order.service.domain.event

import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import java.time.ZonedDateTime

class OrderCancelledEvent(
    override val order: Order,
    override val createdAt: ZonedDateTime,
) : OrderEvent(order, createdAt)