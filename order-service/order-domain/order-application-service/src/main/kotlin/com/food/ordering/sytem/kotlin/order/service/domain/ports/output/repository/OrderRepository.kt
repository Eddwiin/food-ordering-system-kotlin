package com.food.ordering.sytem.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.sytem.kotlin.order.service.domain.entity.Order
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.TrackingId
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository {
    fun save(order: Order): Order
    fun findByTrackingId(trackingId: TrackingId): Optional<Order>
}