package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.entity

import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.util.*

@Table(name = "payments")
@Entity
data class PaymentEntity(
    @Id
    val id: UUID? = null,
    val customerId: UUID? = null,
    val orderId: UUID? = null,
    val price: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    val status: PaymentStatus? = null,
    val createdAt: ZonedDateTime? = null,
)