package com.food.ordering.system.kotlin.domain.valueobject

import java.math.BigDecimal
import java.math.RoundingMode

class Money(val amount: BigDecimal) {
    fun getAmount(): BigDecimal = amount

    companion object {
        val ZERO = Money(BigDecimal.ZERO)
    }

    fun isGreaterThanZero(): Boolean {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0
    }

    fun isGreaterThan(money: Money): Boolean {
        return amount != null && money.getAmount().compareTo(BigDecimal.ZERO) > 0
    }

    fun add(money: Money): Money {
        return Money(setScale(amount.add(money.getAmount())))
    }

    fun subtract(money: Money): Money {
        return Money(setScale(amount.subtract(money.getAmount())))
    }

    fun multiply(multiplier: Int): Money {
        return Money(setScale(amount.multiply(BigDecimal(multiplier))))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Money
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        return amount.hashCode()
    }

    private fun setScale(input: BigDecimal): BigDecimal {
        return input.setScale(2, RoundingMode.HALF_EVEN)
    }
}