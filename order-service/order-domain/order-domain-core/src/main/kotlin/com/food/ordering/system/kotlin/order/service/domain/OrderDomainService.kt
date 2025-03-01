package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent

interface OrderDomainService {
    fun validateAndInitiateOrderMethod(
        order: Order,
        restaurant: Restaurant,
        orderCreatedEventDomainEventPublisher: DomainEventPublisher<OrderCreatedEvent>
    ): OrderCreatedEvent

    fun payOrder(
        order: Order,
        orderPaidEventDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>
    ): OrderPaidEvent

    fun approvedOrder(order: Order)
    fun cancelOrderPayment(
        order: Order,
        failureMessages: MutableList<String>,
        orderCancelledEventDomainEventPublisher: DomainEventPublisher<OrderCancelledEvent>
    ): OrderCancelledEvent

    fun cancelOrder(order: Order, failureMessages: MutableList<String>)
}