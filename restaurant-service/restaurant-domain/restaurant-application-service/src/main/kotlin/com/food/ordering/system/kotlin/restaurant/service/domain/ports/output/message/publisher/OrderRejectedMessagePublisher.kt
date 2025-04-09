package com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.message.publisher

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import event.OrderRejectedEvent

interface OrderRejectedMessagePublisher : DomainEventPublisher<OrderRejectedEvent>