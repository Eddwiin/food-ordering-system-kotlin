package com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditEntry

interface CreditEntryRepository {
    fun save(creditEntry: CreditEntry): CreditEntry;
    fun findByCustomerId(customerId: CustomerId): CreditEntry?;
}