package entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import valueobject.PaymentId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Payment(
    var paymentStatus: PaymentStatus,
    val orderId: OrderId,
    val customerId: CustomerId,
    val price: Money,
    var createdAt: ZonedDateTime
) : AggregateRoot<PaymentId>() {

    fun validatePayment(failureMessages: MutableList<String>) {
        if (price == null || !price.isGreaterThanZero()) {
            failureMessages.add("Total price must be greater than zero!");
        }
    }

    fun initializePayment() {
        this.id = PaymentId(UUID.randomUUID())
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"))
    }

    fun updateStatus(completed: PaymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}