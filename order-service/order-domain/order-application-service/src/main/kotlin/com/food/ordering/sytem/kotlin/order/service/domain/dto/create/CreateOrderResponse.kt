package com.food.ordering.sytem.kotlin.order.service.domain.dto.create

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.TrackingId
import jakarta.validation.constraints.NotNull

class CreateOrderResponse(
    @field:NotNull
    val orderTrackingId: TrackingId,
    @field:NotNull
    val orderStatus: OrderStatus,
    @field:NotNull
    val message: String
) {
    companion object {
        fun builder() = CreateOrderResponseBuilder()
    }

    class CreateOrderResponseBuilder {
        private var orderTrackingId: TrackingId? = null
        private var orderStatus: OrderStatus? = null
        private var message: String? = null

        fun orderTrackingId(orderTrackingId: TrackingId) = apply { this.orderTrackingId = orderTrackingId }
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