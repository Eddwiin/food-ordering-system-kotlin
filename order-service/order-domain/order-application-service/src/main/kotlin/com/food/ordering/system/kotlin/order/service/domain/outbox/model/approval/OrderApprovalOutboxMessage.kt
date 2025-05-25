package com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval

import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import java.time.ZonedDateTime
import java.util.*

data class OrderApprovalOutboxMessage(
    val id: UUID,
    val sagaId: UUID,
    val createdAt: ZonedDateTime? = null,
    val processedAt: ZonedDateTime? = null,
    val type: String,
    val payload: String? = null,
    val sagaStatus: SagaStatus? = null,
    val orderStatus: OrderStatus? = null,
    val outboxStatus: OutboxStatus? = null,
    val version: Int = 0
)