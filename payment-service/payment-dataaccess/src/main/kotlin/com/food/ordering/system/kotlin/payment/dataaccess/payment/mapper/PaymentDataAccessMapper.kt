package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.mapper

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.entity.PaymentEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import com.food.ordering.system.kotlin.payment.service.domain.core.valueobject.PaymentId


class PaymentDataAccessMapper {

    fun paymentToPaymentEntity(payment: Payment): PaymentEntity {
        return PaymentEntity(
            id = payment.id?.value,
            customerId = payment.customerId.value,
            orderId = payment.orderId.value,
            price = payment.price.amount,
            status = payment.paymentStatus,
            createdAt = payment.createdAt
        )
    }

    fun paymentEntityToPayment(paymentEntity: PaymentEntity?): Payment {
        return Payment(
            paymentId = PaymentId(paymentEntity?.id!!),
            customerId = CustomerId(paymentEntity?.customerId!!),
            orderId = OrderId(paymentEntity.orderId!!),
            price = Money(paymentEntity.price!!)
        )
    }

}