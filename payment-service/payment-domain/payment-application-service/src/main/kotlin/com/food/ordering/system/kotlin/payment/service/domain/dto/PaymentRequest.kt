package com.food.ordering.system.kotlin.payment.service.domain.dto

import com.food.ordering.system.kotlin.domain.valueobject.PaymentOrderStatus
import java.math.BigDecimal
import java.time.Instant

class PaymentRequest(
    val id: String? = null,
    val sagaId: String? = null,
    val orderId: String? = null,
    val customerId: String? = null,
    val price: BigDecimal? = null,
    val createdAt: Instant? = null,
    val paymentOrderStatus: PaymentOrderStatus? = null,
)