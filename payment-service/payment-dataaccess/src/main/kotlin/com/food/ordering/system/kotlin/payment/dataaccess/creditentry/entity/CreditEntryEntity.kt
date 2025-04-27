package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Table(name = "credit_entry")
@Entity
data class CreditEntryEntity(
    @Id
    val id: UUID? = null,
    val customerId: UUID? = null,
    val totalCreditAmount: BigDecimal? = null
)