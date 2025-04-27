package com.food.ordering.system.kotlin.payment.service.domain.core.event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.Payment
import java.time.ZonedDateTime

class PaymentCancelledEvent(
    override val payment: Payment,
    override val createAt: ZonedDateTime,
    val paymentCancelledEventDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>
) : PaymentEvent(payment, createAt, mutableListOf()) {

    override fun fire() {
        paymentCancelledEventDomainEventPublisher.publish(this)
    }
}