package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.entity

import com.food.ordering.system.kotlin.payment.service.domain.core.valueobject.TransactionType
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Table(name = "credit_history")
@Entity
data class CreditHistoryEntity(
    @Id
    val id: UUID,
    val customerId: UUID,
    val amount: BigDecimal,
    @Enumerated(EnumType.STRING)
    val type: TransactionType

)