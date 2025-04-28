package com.food.ordering.system.kotlin.payment.service.domain.core.event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentCompletedEvent(
    override val payment: Payment?,
    override val createAt: ZonedDateTime,
    val paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>?
) :
    PaymentEvent(payment, createAt, mutableListOf()) {

    override fun fire() {
        paymentCompletedEventDomainEventPublisher?.publish(this)
    }
}