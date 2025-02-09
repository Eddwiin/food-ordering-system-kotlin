package com.food.ordering.sytem.kotlin.order.service.domain.ports.output.publisher.restaurantapproval

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderPaidEvent

interface OrderPaidRestaurantRequestMessagePublisher : DomainEventPublisher<OrderPaidEvent> {
}