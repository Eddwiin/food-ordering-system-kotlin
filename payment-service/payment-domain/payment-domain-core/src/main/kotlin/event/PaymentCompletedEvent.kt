package event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.Payment
import java.time.ZonedDateTime

class PaymentCompletedEvent(
    override val payment: Payment,
    override val createAt: ZonedDateTime,
    val paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>
) :
    PaymentEvent(payment, createAt, mutableListOf()) {

    override fun fire() {
        paymentCompletedEventDomainEventPublisher.publish(this)
    }
}