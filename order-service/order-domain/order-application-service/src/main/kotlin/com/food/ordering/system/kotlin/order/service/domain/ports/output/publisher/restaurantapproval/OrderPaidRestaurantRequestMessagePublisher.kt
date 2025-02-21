package com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.restaurantapproval

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher

interface OrderPaidRestaurantRequestMessagePublisher :
    DomainEventPublisher<com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent> {
}