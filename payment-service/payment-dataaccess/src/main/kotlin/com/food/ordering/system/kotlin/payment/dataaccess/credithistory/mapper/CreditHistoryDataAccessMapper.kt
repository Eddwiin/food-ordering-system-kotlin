package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.mapper


import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.entity.CreditHistoryEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditHistory
import com.food.ordering.system.kotlin.payment.service.domain.core.valueobject.CreditHistoryId
import org.springframework.stereotype.Component

@Component
class CreditHistoryDataAccessMapper {

    fun creditHistoryEntityToCreditHistory(creditHistoryEntity: CreditHistoryEntity): CreditHistory {
        return CreditHistory(
            creditHistoryId = CreditHistoryId(creditHistoryEntity.id),
            customerId = CustomerId(creditHistoryEntity.customerId),
            amount = Money(creditHistoryEntity.amount),
            transactionType = creditHistoryEntity.type
        )
    }

    fun creditHistoryToCreditHistoryEntity(creditHistory: CreditHistory): CreditHistoryEntity {
        return CreditHistoryEntity(
            id = creditHistory.id?.value!!,
            customerId = creditHistory.customerId.value!!,
            amount = creditHistory.amount.amount,
            type = creditHistory.transactionType
        )
    }


}