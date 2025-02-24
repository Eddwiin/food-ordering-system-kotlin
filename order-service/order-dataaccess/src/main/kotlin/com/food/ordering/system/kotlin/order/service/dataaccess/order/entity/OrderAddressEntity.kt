package com.food.ordering.system.kotlin.order.service.dataaccess.order.entity

import jakarta.persistence.*
import java.util.*

@Table(name = "order_address")
@Entity
data class OrderAddressEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "ORDER_ID")
    var order: OrderEntity? = null,

    val street: String? = null,
    val postalCode: String? = null,
    val city: String? = null,
) {

    companion object {
        fun builder() = OrderAddressEntityBuilder()
    }

    constructor() : this(
        id = null,
        order = null,
        street = "",
        postalCode = "",
        city = ""
    )

    class OrderAddressEntityBuilder {
        private lateinit var id: UUID
        private lateinit var order: OrderEntity
        private lateinit var street: String
        private lateinit var postalCode: String
        private lateinit var city: String

        fun id(id: UUID) = apply { this.id = id }
        fun order(order: OrderEntity) = apply { this.order = order }
        fun street(street: String) = apply { this.street = street }
        fun postalCode(postalCode: String) = apply { this.postalCode = postalCode }
        fun city(city: String) = apply { this.city = city }

        fun build(): OrderAddressEntity {
            return OrderAddressEntity(
                id = id,
                order = order,
                street = street,
                postalCode = postalCode,
                city = city
            )
        }
    }
}
