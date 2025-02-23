package com.food.ordering.system.kotlin.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@IdClass(OrderItemEntityId::class)
@Table(name = "order_items")
@Entity
data class OrderItemEntity(
    @Id
    val id: Long,

    @Id
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    val order: OrderEntity? = null,

    val productId: UUID? = null,
    val price: BigDecimal? = null,
    val quantity: Int? = null,
    val subTotal: BigDecimal? = null
) {

    companion object {
        fun builder() = OrderItemEntityBuilder()
    }

    constructor() : this(
        id = 0L,
        order = null
    )

    class OrderItemEntityBuilder {
        private var id: Long? = null
        private var order: OrderEntity? = null
        private var productId: UUID? = null
        private var price: BigDecimal? = null
        private var quantity: Int? = null
        private var subTotal: BigDecimal? = null

        fun id(id: Long) = apply { this.id = id }
        fun order(order: OrderEntity?) = apply { this.order = order }
        fun productId(productId: UUID?) = apply { this.productId = productId }
        fun price(price: BigDecimal?) = apply { this.price = price }
        fun quantity(quantity: Int?) = apply { this.quantity = quantity }
        fun subTotal(subTotal: BigDecimal?) = apply { this.subTotal = subTotal }

        fun build(): OrderItemEntity {
            requireNotNull(id) { "id must not be null" }
            return OrderItemEntity(
                id = id!!,
                order = order,
                productId = productId,
                price = price,
                quantity = quantity,
                subTotal = subTotal
            )
        }
    }
}