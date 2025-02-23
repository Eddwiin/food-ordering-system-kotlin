package com.food.ordering.system.kotlin.order.service.dataaccess.order.entity

import java.io.Serializable

data class OrderItemEntityId(
    val id: Long,
    val order: OrderEntity? = null
) : Serializable {

    companion object {
        fun builder() = OrderItemEntityIdBuilder()
    }

    class OrderItemEntityIdBuilder {
        private var id: Long? = null
        private var order: OrderEntity? = null

        fun id(id: Long) = apply { this.id = id }
        fun order(order: OrderEntity?) = apply { this.order = order }

        fun build(): OrderItemEntityId {
            requireNotNull(id) { "id must not be null" }
            return OrderItemEntityId(
                id = id!!,
                order = order
            )
        }
    }
}