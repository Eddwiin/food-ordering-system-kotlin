package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import java.util.*

interface PaymentOutboxRepository {
    fun save(orderPaymentOutboxMessage: OrderPaymentOutboxMessage): OrderPaymentOutboxMessage?

    fun findByTypeAndOutboxStatus(
        type: String,
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    ): List<OrderPaymentOutboxMessage>?

    fun findByTypeAndSagaIdAndSagaStatus(
        type: String,
        sagaId: UUID,
        vararg sagaStatus: SagaStatus
    ): OrderPaymentOutboxMessage?

    fun deleteByTypeAndOutboxStatusAndSagaStatus(
        type: String,
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    )

}