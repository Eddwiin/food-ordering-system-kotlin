package com.food.ordering.system.kotlin.payment.service.domain.ports.output.message.publisher

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import event.PaymentFailedEvent

interface PaymentFailedMessagePublisher : DomainEventPublisher<PaymentFailedEvent>