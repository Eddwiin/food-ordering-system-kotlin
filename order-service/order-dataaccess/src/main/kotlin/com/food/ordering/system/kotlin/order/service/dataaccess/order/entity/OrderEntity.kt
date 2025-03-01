package com.food.ordering.system.kotlin.order.service.dataaccess.order.entity

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Table(name = "orders")
@Entity
data class OrderEntity(
    @Id
    val id: UUID? = null,
    val customerId: UUID? = null,
    val restaurantId: UUID? = null,
    val trackingId: UUID? = null,
    val price: BigDecimal? = null,
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus? = null,
    val failureMessages: String? = null,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL])
    val address: OrderAddressEntity? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "order", orphanRemoval = true)
    val items: List<OrderItemEntity> = emptyList()
) {
    companion object {
        fun builder() = OrderEntityBuilder()
    }

    constructor() : this(
        id = null,
        customerId = null,
        restaurantId = null,
        trackingId = null,
        price = null,
        orderStatus = null,
        failureMessages = "",
        address = null
    )


    class OrderEntityBuilder {
        private var id: UUID? = null
        private var customerId: UUID? = null
        private var restaurantId: UUID? = null
        private var trackingId: UUID? = null
        private var price: BigDecimal? = null
        private var orderStatus: OrderStatus? = null
        private var failureMessages: String? = null
        private var address: OrderAddressEntity? = null
        private var items: List<OrderItemEntity> = emptyList()

        fun id(id: UUID?) = apply { this.id = id }
        fun customerId(customerId: UUID?) = apply { this.customerId = customerId }
        fun restaurantId(restaurantId: UUID?) = apply { this.restaurantId = restaurantId }
        fun trackingId(trackingId: UUID?) = apply { this.trackingId = trackingId }
        fun price(price: BigDecimal?) = apply { this.price = price }
        fun orderStatus(orderStatus: OrderStatus?) = apply { this.orderStatus = orderStatus }
        fun failureMessages(failureMessages: String?) = apply { this.failureMessages = failureMessages }
        fun address(address: OrderAddressEntity?) = apply { this.address = address }
        fun items(items: List<OrderItemEntity>) = apply { this.items = items }

        fun build(): OrderEntity {
            return OrderEntity(
                id = id,
                customerId = customerId,
                restaurantId = restaurantId,
                trackingId = trackingId,
                price = price,
                orderStatus = orderStatus,
                failureMessages = failureMessages,
                address = address,
                items = items
            )
        }
    }
}