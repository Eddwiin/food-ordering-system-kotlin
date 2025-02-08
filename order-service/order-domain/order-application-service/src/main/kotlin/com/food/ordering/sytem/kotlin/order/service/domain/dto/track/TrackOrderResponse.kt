package com.food.ordering.sytem.kotlin.order.service.domain.dto.track

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.TrackingId
import jakarta.validation.constraints.NotNull

class TrackOrderResponse(
    @field:NotNull
    val orderTrackingId: TrackingId,
    @field:NotNull
    val orderStatus: OrderStatus,
    val failureMessages: MutableList<String>
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var orderTrackingId: TrackingId? = null
        private var orderStatus: OrderStatus? = null
        private var failureMessages: MutableList<String> = mutableListOf()

        fun orderTrackingId(orderTrackingId: TrackingId) = apply { this.orderTrackingId = orderTrackingId }
        fun orderStatus(orderStatus: OrderStatus) = apply { this.orderStatus = orderStatus }
        fun failureMessages(failureMessages: MutableList<String>) = apply { this.failureMessages = failureMessages }

        fun build(): TrackOrderResponse {
            requireNotNull(orderTrackingId) { "orderTrackingId cannot be null" }
            requireNotNull(orderStatus) { "orderStatus cannot be null" }
            return TrackOrderResponse(orderTrackingId!!, orderStatus!!, failureMessages)
        }
    }
}