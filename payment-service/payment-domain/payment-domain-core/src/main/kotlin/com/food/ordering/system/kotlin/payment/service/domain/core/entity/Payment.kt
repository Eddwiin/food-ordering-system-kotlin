package com.food.ordering.system.kotlin.payment.service.domain.core.entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import com.food.ordering.system.kotlin.payment.service.domain.core.valueobject.PaymentId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class Payment(
    var paymentId: PaymentId? = null,
    var paymentStatus: PaymentStatus? = null,
    val orderId: OrderId,
    val customerId: CustomerId,
    val price: Money,
    var createdAt: ZonedDateTime? = null
) : AggregateRoot<PaymentId>() {

    fun validatePayment(failureMessages: MutableList<String>?) {
        if (price == null || !price.isGreaterThanZero()) {
            failureMessages?.add("Total price must be greater than zero!");
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