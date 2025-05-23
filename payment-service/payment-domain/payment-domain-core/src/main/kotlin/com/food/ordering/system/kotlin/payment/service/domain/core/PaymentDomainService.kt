package com.food.ordering.system.kotlin.payment.service.domain.core

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditEntry
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditHistory
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentCancelledEvent
import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentCompletedEvent
import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentEvent
import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentFailedEvent

interface PaymentDomainService {

    fun validateAndInitiatePayment(
        payment: Payment? = null,
        creditEntry: CreditEntry? = null,
        creditHistories: MutableList<CreditHistory>? = null,
        failureMessages: MutableList<String>? = null,
        paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>? = null,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>? = null,
        paymentCancelledEventDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>? = null
    ): PaymentEvent

    fun validateAndCancelPayment(
        payment: Payment,
        creditEnty: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCancelledEventDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent
}