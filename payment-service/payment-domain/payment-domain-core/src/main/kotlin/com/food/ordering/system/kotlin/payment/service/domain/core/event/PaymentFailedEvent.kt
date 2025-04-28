package com.food.ordering.system.kotlin.payment.service.domain.core.event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import java.time.ZonedDateTime

class PaymentFailedEvent(
    override val payment: Payment?,
    override val createAt: ZonedDateTime,
    override val failureMessages: MutableList<String>?,
    val paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>?
) : PaymentEvent(payment, createAt, failureMessages) {

    override fun fire() {
        paymentFailedEventDomainEventPublisher?.publish(this)
    }

}