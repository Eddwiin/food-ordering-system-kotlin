package event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.Payment
import java.time.ZonedDateTime

class PaymentFailedEvent(
    override val payment: Payment,
    override val createAt: ZonedDateTime,
    override val failureMessages: MutableList<String>,
    val paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
) : PaymentEvent(payment, createAt, failureMessages) {

    override fun fire() {
        paymentFailedEventDomainEventPublisher.publish(this)
    }

}