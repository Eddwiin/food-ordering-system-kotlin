package com.food.ordering.sytem.kotlin.order.service.domain.dto.create

import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.util.*

class OrderItem(
    @field:NotNull
    val productId: UUID,
    @field:NotNull
    val quantity: Int,
    @field:NotNull
    val price: BigDecimal,
    @field:NotNull
    val subTotal: BigDecimal
) {
    companion object {
        fun builder() = OrderItemBuilder()
    }

    class OrderItemBuilder {
        private var productId: UUID? = null
        private var quantity: Int? = null
        private var price: BigDecimal? = null
        private var subTotal: BigDecimal? = null

        fun productId(productId: UUID) = apply { this.productId = productId }
        fun quantity(quantity: Int) = apply { this.quantity = quantity }
        fun price(price: BigDecimal) = apply { this.price = price }
        fun subTotal(subTotal: BigDecimal) = apply { this.subTotal = subTotal }

        fun build(): OrderItem {
            return OrderItem(
                productId = productId ?: throw IllegalArgumentException("ProductId cannot be null"),
                quantity = quantity ?: throw IllegalArgumentException("Quantity cannot be null"),
                price = price ?: throw IllegalArgumentException("Price cannot be null"),
                subTotal = subTotal ?: throw IllegalArgumentException("SubTotal cannot be null")
            )
        }
    }

}