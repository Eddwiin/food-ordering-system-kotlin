package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.mapper

import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import com.food.ordering.system.kotlin.payment.service.domain.dto.PaymentRequest
import org.springframework.stereotype.Component
import java.util.*


@Component
class PaymentDataMapper {

    fun paymentRequestModelToPayment(paymentRequest: PaymentRequest): Payment {
        return Payment(
            orderId = OrderId(UUID.fromString(paymentRequest.orderId)),
            customerId = CustomerId(UUID.fromString(paymentRequest.customerId)),
            price = Money(paymentRequest.price!!),
        )
    }
}