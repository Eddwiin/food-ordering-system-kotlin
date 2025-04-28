package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.mapper


import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.entity.CreditEntryEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditEntry
import com.food.ordering.system.kotlin.payment.service.domain.core.valueobject.CreditEntryId
import org.springframework.stereotype.Component

@Component
class CreditEntryDataAccessMapper {

    fun creditEntryEntityToCreditEntry(creditEntryEntity: CreditEntryEntity?): CreditEntry {
        return CreditEntry(
            creditEntryId = CreditEntryId(creditEntryEntity?.id!!),
            customerId = CustomerId(creditEntryEntity.customerId!!),
            totalCreditAmount = Money(creditEntryEntity.totalCreditAmount!!)
        )
    }

    fun creditEntryToCreditEntryEntity(creditEntry: CreditEntry): CreditEntryEntity {
        return CreditEntryEntity(
            id = creditEntry.id?.value!!,
            customerId = creditEntry.customerId.value,
            totalCreditAmount = creditEntry.totalCreditAmount.amount
        )
    }

}