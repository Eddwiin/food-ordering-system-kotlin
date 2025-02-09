package com.food.ordering.sytem.kotlin.order.service.domain.dto.create

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import jakarta.validation.constraints.NotNull
import java.util.*

class CreateOrderResponse(
    @field:NotNull
    val orderTrackingId: UUID,
    @field:NotNull
    val orderStatus: OrderStatus,
    @field:NotNull
    val message: String
) {
    companion object {
        fun builder() = CreateOrderResponseBuilder()
    }

    class CreateOrderResponseBuilder {
        private var orderTrackingId: UUID? = null
        private var orderStatus: OrderStatus? = null
        private var message: String? = null

        fun orderTrackingId(orderTrackingId: UUID) = apply { this.orderTrackingId = orderTrackingId }
        fun orderStatus(orderStatus: OrderStatus) = apply { this.orderStatus = orderStatus }
        fun message(message: String) = apply { this.message = message }

        fun build(): CreateOrderResponse {
            return CreateOrderResponse(
                orderTrackingId = requireNotNull(orderTrackingId) { "orderTrackingId must not be null" },
                orderStatus = requireNotNull(orderStatus) { "orderStatus must not be null" },
                message = requireNotNull(message) { "message must not be null" }
            )
        }
    }
}