package com.food.ordering.system.kotlin.payment.service.domain.core.entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import valueobject.CreditEntryId


class CreditEntry(
    val creditEntryId: CreditEntryId,
    val customerId: CustomerId,
    var totalCreditAmount: Money
) : BaseEntity<CreditEntryId>() {
    fun subtractCreditAmount(amount: Money?) {
        totalCreditAmount = totalCreditAmount.subtract(amount!!)
    }

    fun addCreditAmount(amount: Money?) {
        totalCreditAmount = totalCreditAmount.add(amount!!)
    }
}