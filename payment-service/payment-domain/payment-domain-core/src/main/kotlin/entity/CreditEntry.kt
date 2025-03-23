package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.domain.valueobject.Money
import valueobject.CreditentryId


class CreditEntry(
    val customerId: CustomerId,
    var totalCreditAmount: Money
) : BaseEntity<CreditentryId>() {
    fun subtractCreditAmount(amount: Money?) {
        totalCreditAmount = totalCreditAmount.subtract(amount!!)
    }
}