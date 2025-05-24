package com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.ZonedDateTime

class OrderPaymentEventPayload(
    @JsonProperty
    val orderId: String,
    @JsonProperty
    val customerId: String,
    @JsonProperty
    val price: BigDecimal,
    @JsonProperty
    val createdAt: ZonedDateTime,
    @JsonProperty
    val paymentOrderStatus: String,
)