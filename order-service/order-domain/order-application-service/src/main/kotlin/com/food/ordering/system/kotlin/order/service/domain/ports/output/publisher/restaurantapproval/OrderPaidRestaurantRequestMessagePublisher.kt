package com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.restaurantapproval

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent

interface OrderPaidRestaurantRequestMessagePublisher :
    DomainEventPublisher<OrderPaidEvent> {
}