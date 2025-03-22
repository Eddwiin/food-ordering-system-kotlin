package event

import com.food.ordering.system.kotlin.domain.event.DomainEvent
import entity.Payment
import java.time.ZonedDateTime

abstract class PaymentEvent(
    open val payment: Payment,
    open val createAt: ZonedDateTime,
    open val failureMessages: List<String>
) : DomainEvent<Payment>