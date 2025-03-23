package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import valueobject.CreditHistoryId
import valueobject.TransactionType

class CreditHistory(
    val creditHistoryId: CreditHistoryId,
    val customerId: CustomerId,
    val amount: Money,
    val transactionType: TransactionType
) : BaseEntity<CreditHistoryId>()