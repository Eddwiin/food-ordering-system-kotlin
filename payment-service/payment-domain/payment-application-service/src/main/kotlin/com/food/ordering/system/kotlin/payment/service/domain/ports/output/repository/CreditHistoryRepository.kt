package com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import entity.CreditHistory

interface CreditHistoryRepository {
    fun save(creditHistory: CreditHistory): CreditHistory;
    fun findByCustomerId(customerId: CustomerId): MutableList<CreditHistory>?
}