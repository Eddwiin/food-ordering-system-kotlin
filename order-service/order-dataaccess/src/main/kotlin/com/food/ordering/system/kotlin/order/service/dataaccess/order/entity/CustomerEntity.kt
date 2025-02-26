package com.food.ordering.system.kotlin.order.service.dataaccess.order.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "order_customer", schema = "customer")
@Entity
class CustomerEntity(
    @Id
    val id: UUID? = null
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var id: UUID? = null

        fun id(id: UUID?) = apply { this.id = id }

        fun build(): CustomerEntity {
            return CustomerEntity(
                id = id
            )
        }
    }
}