package com.food.ordering.system.kotlin.order.service.domain.dto.track

import jakarta.validation.constraints.NotNull
import java.util.*

class TrackOrderQuery(
    @field:NotNull
    val orderTrackingId: UUID,
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private var orderTrackingId: UUID? = null

        fun orderTrackingId(orderTrackingId: UUID) = apply {
            this.orderTrackingId = orderTrackingId
        }

        fun build(): TrackOrderQuery {
            requireNotNull(orderTrackingId) { "orderTrackingId must not be null" }
            return TrackOrderQuery(orderTrackingId!!)
        }
    }
}