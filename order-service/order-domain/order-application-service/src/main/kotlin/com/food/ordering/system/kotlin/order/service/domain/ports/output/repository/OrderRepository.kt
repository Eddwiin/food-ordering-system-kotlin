package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId


interface OrderRepository {
    fun save(order: Order): Order
    fun findByTrackingId(trackingId: TrackingId): Order?
    fun findById(orderId: OrderId): Order?
}