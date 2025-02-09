package com.food.ordering.sytem.kotlin.order.service.domain.dto.create

import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.util.*

class CreateOrderCommand(
    @field:NotNull
    val customerId: UUID,
    @field:NotNull
    val restaurantId: UUID,
    @field:NotNull
    val price: BigDecimal,
    @field:NotNull
    val items: List<OrderItem>,
    @field:NotNull
    val address: OrderAddress
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var customerId: UUID? = null
        private var restaurantId: UUID? = null
        private var price: BigDecimal? = null
        private var items: List<OrderItem> = emptyList()
        private var address: OrderAddress? = null

        fun customerId(customerId: UUID) = apply { this.customerId = customerId }
        fun restaurantId(restaurantId: UUID) = apply { this.restaurantId = restaurantId }
        fun price(price: BigDecimal) = apply { this.price = price }
        fun items(items: List<OrderItem>) = apply { this.items = items }
        fun address(address: OrderAddress) = apply { this.address = address }

        fun build(): CreateOrderCommand {
            requireNotNull(customerId) { "Customer ID must not be null" }
            requireNotNull(restaurantId) { "Restaurant ID must not be null" }
            requireNotNull(price) { "Price must not be null" }
            require(items.isNotEmpty()) { "Items must not be empty" }
            requireNotNull(address) { "Address must not be null" }

            return CreateOrderCommand(
                customerId = customerId!!,
                restaurantId = restaurantId!!,
                price = price!!,
                items = items,
                address = address!!
            )
        }
    }
}