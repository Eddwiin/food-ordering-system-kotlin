package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import java.util.*

interface ApprovalOutboxRepository {
    fun save(orderApprovalOutboxMessage: OrderApprovalOutboxMessage): OrderApprovalOutboxMessage

    fun findByTypeAndOutboxStatus(
        type: String,
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    ): List<OrderApprovalOutboxMessage>?

    fun findByTypeAndSagaIdAndSagaStatus(
        type: String,
        sagaId: UUID,
        vararg sagaStatus: SagaStatus
    ): OrderApprovalOutboxMessage?

    fun deleteByTypeAndOutboxStatusAndSagaStatus(
        type: String,
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    )
}