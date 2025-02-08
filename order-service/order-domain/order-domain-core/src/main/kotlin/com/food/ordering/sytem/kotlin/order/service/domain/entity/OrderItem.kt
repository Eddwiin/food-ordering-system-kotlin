package com.food.ordering.sytem.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.OrderItemId
import java.util.*

class OrderItem private constructor(
    var orderId: OrderId?,
    var product: Product,
    var quantity: Int,
    var price: Money,
    var subTotal: Money?
) : BaseEntity<OrderItemId>() {

    internal fun initializeOrderItems(orderId: OrderId?, orderItemId: OrderItemId) {
        this.orderId = orderId;
        this.id = orderItemId;
    }

    companion object {
        fun builder() = Builder()
    }

    init {
        this.id = builder().orderItemId
    }

    fun isPriceValid(): Boolean {
        return price.isGreaterThanZero()
                && price.equals(product.price)
                && price.multiply(quantity).equals(subTotal)
    }

    class Builder {
        var orderItemId: OrderItemId? = null
        var product: Product? = null
        var quantity: Int = 0
        var price: Money? = null
        var subTotal: Money? = null

        fun orderItemId(orderItemId: OrderItemId) = apply { this.orderItemId = orderItemId }
        fun product(product: Product) = apply { this.product = product }
        fun quantity(quantity: Int) = apply { this.quantity = quantity }
        fun price(price: Money) = apply { this.price = price }
        fun subTotal(subTotal: Money) = apply { this.subTotal = subTotal }

        fun build(): OrderItem {
            requireNotNull(orderItemId) { "Order Item Id must not be null" }
            requireNotNull(product) { "Product must not be null" }
            requireNotNull(price) { "Price must not be null" }
            requireNotNull(subTotal) { "Sub-total must not be null" }
            require(quantity > 0) { "Quantity must be greater than zero" }

            return OrderItem(
                orderId = OrderId(UUID.randomUUID()), // Placeholder since OrderId is not part of builder
                product = product!!,
                quantity = quantity,
                price = price!!,
                subTotal = subTotal!!
            )
        }
    }
}
