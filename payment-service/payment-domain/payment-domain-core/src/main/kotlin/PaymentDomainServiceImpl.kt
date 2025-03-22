import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.CreditEntry
import entity.CreditHistory
import entity.Payment
import event.PaymentCancelledEvent
import event.PaymentCompletedEvent
import event.PaymentEvent
import event.PaymentFailedEvent

class PaymentDomainServiceImpl : PaymentDomainService {
    override fun validateAndInitiatePayment(
        payment: Payment,
        creditEnty: CreditEntry,
        creditHistories: List<CreditHistory>,
        failureMessages: List<String>,
        paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>
    ): PaymentEvent {
        TODO("Not yet implemented")
    }

    override fun validateAndCancelPayment(
        payment: Payment,
        creditEnty: CreditEntry,
        creditHistories: List<CreditHistory>,
        failureMessages: List<String>,
        paymentCancelledEventDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {
        TODO("Not yet implemented")
    }
}