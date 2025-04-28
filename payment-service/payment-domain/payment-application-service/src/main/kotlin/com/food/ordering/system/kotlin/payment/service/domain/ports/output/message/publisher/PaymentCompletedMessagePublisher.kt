package com.food.ordering.system.kotlin.payment.service.domain.ports.output.message.publisher

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentCompletedEvent

interface PaymentCompletedMessagePublisher : DomainEventPublisher<PaymentCompletedEvent>