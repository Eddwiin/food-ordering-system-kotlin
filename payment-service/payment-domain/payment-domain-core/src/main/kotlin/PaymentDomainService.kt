import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.CreditEntry
import entity.CreditHistory
import entity.Payment
import event.PaymentCancelledEvent
import event.PaymentCompletedEvent
import event.PaymentEvent
import event.PaymentFailedEvent

interface PaymentDomainService {

    fun validateAndInitiatePayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
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