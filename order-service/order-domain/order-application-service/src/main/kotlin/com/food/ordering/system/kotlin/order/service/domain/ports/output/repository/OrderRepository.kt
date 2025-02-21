package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository {
    fun save(order: Order): Order
    fun findByTrackingId(trackingId: com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId): Optional<Order>
}