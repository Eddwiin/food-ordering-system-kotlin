package com.food.ordering.system.kotlin.order.service.domain.dto.message

import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import java.math.BigDecimal
import java.time.Instant

class PaymentResponse(
    val id: String,
    val sagaId: String,
    val orderId: String,
    val paymentId: String,
    val customerId: String,
    val price: BigDecimal,
    val createdAt: Instant,
    val paymentStatus: PaymentStatus
) {
    companion object {
        fun builder() = PaymentResponseBuilder()
    }

    class PaymentResponseBuilder {
        private var id: String = ""
        private var sagaId: String = ""
        private var orderId: String = ""
        private var paymentId: String = ""
        private var customerId: String = ""
        private var price: BigDecimal = BigDecimal.ZERO
        private var createdAt: Instant = Instant.now()
        private var paymentStatus: PaymentStatus = PaymentStatus.COMPLETED

        fun id(id: String) = apply { this.id = id }
        fun sagaId(sagaId: String) = apply { this.sagaId = sagaId }
        fun orderId(orderId: String) = apply { this.orderId = orderId }
        fun paymentId(paymentId: String) = apply { this.paymentId = paymentId }
        fun customerId(customerId: String) = apply { this.customerId = customerId }
        fun price(price: BigDecimal) = apply { this.price = price }
        fun createdAt(createdAt: Instant) = apply { this.createdAt = createdAt }
        fun paymentStatus(paymentStatus: PaymentStatus) = apply { this.paymentStatus = paymentStatus }

        fun build(): PaymentResponse {
            return PaymentResponse(
                id = this.id,
                sagaId = this.sagaId,
                orderId = this.orderId,
                paymentId = this.paymentId,
                customerId = this.customerId,
                price = this.price,
                createdAt = this.createdAt,
                paymentStatus = this.paymentStatus
            )
        }
    }
}