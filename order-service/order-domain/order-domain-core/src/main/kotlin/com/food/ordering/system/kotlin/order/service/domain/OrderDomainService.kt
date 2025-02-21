package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant

interface OrderDomainService {
    fun validateAndInitiateOrderMethod(
        order: Order,
        restaurant: Restaurant,
        orderCreatedEventDomainEventPublisher: DomainEventPublisher<com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent>
    ): com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent

    fun payOrder(
        order: Order,
        orderPaidEventDomainEventPublisher: DomainEventPublisher<com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent>
    ): com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent

    fun approvedOrder(order: Order)
    fun cancelOrderPayment(
        order: Order,
        failureMessages: MutableList<String>,
        orderCancelledEventDomainEventPublisher: DomainEventPublisher<com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent>
    ): com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent

    fun cancelOrder(order: Order, failureMessages: MutableList<String>)
}