package com.food.ordering.sytem.kotlin.order.service.domain

import com.food.ordering.sytem.kotlin.order.service.domain.entity.Order
import com.food.ordering.sytem.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderPaidEvent

interface OrderDomainService {
    fun validateAndInitiateOrderMethod(order: Order, restaurant: Restaurant): OrderCreatedEvent
    fun payOrder(order: Order): OrderPaidEvent
    fun approvedOrder(order: Order)
    fun cancelOrderPayment(order: Order, failureMessages: MutableList<String>): OrderCancelledEvent
    fun cancelOrder(order: Order, failureMessages: MutableList<String>)
}