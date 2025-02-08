package com.food.ordering.sytem.kotlin.order.service.domain.dto.create

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull

class OrderAddress(
    @field:NotNull
    @field:Max(value = 50)
    val street: String,

    @field:NotNull
    @field:Max(value = 50)
    val postalCode: String,

    @field:NotNull
    @field:Max(value = 50)
    val city: String,
) {

    companion object {
        fun builder() = OrderAddressBuilder()
    }

    class OrderAddressBuilder {
        private var street: String? = null
        private var postalCode: String? = null
        private var city: String? = null

        fun street(street: String) = apply { this.street = street }
        fun postalCode(postalCode: String) = apply { this.postalCode = postalCode }
        fun city(city: String) = apply { this.city = city }

        fun build(): OrderAddress {
            return OrderAddress(
                street = requireNotNull(street) { "Street must not be null" },
                postalCode = requireNotNull(postalCode) { "Postal code must not be null" },
                city = requireNotNull(city) { "City must not be null" }
            )
        }
    }
}
