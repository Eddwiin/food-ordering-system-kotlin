package com.food.ordering.system.kotlin.order.service.domain.dto.message

import com.food.ordering.system.kotlin.domain.valueobject.OrderApprovalStatus
import java.time.Instant

class RestaurantApprovalResponse(
    val id: String,
    val sagaId: String,
    val orderId: String,
    val restaurantId: String,
    val createdAt: Instant,
    val orderApprovalStatus: OrderApprovalStatus,
    val failureMessage: MutableList<String>
) {
    companion object {
        fun builder() = Builder()
    }

    class Builder {
        private lateinit var id: String
        private lateinit var sagaId: String
        private lateinit var orderId: String
        private lateinit var restaurantId: String
        private lateinit var createdAt: Instant
        private lateinit var orderApprovalStatus: OrderApprovalStatus
        private val failureMessage: MutableList<String> = mutableListOf()

        fun id(id: String) = apply { this.id = id }
        fun sagaId(sagaId: String) = apply { this.sagaId = sagaId }
        fun orderId(orderId: String) = apply { this.orderId = orderId }
        fun restaurantId(restaurantId: String) = apply { this.restaurantId = restaurantId }
        fun createdAt(createdAt: Instant) = apply { this.createdAt = createdAt }
        fun orderApprovalStatus(orderApprovalStatus: OrderApprovalStatus) =
            apply { this.orderApprovalStatus = orderApprovalStatus }

        fun failureMessage(failureMessage: List<String>) = apply { this.failureMessage.addAll(failureMessage) }

        fun build(): RestaurantApprovalResponse {
            return RestaurantApprovalResponse(
                id = id,
                sagaId = sagaId,
                orderId = orderId,
                restaurantId = restaurantId,
                createdAt = createdAt,
                orderApprovalStatus = orderApprovalStatus,
                failureMessage = failureMessage
            )
        }
    }
}