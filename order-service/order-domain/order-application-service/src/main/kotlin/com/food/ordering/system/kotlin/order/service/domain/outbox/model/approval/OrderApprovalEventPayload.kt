package com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.ZonedDateTime

class OrderApprovalEventPayload(
    @JsonProperty
    val orderId: String,
    @JsonProperty
    val restaurantId: String,
    @JsonProperty
    val price: BigDecimal,
    @JsonProperty
    val createdAt: ZonedDateTime,
    @JsonProperty
    val restaurantOrderStatus: String,
    @JsonProperty
    val products: List<OrderApprovalEventProduct>
)