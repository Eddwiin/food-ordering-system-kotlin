package com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import java.time.ZonedDateTime
import java.util.*

data class OrderPaymentOutboxMessage(
    val id: UUID,
    val sagaId: UUID,
    val createAt: ZonedDateTime,
    var processedAt: ZonedDateTime? = null,
    val type: String,
    val payload: String,
    var sagaStatus: SagaStatus,
    var orderStatus: OrderStatus,
    var outboxStatus: OutboxStatus,
    val version: Int? = 0
)