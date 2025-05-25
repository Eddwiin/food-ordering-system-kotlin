package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalEventPayload
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.ApprovalOutboxRepository
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import com.food.ordering.system.kotlin.saga.order.SagaConstants
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
open class ApprovalOutboxHelper(
    val approvalOutboxRepository: ApprovalOutboxRepository,
    val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger { }

    @Transactional(readOnly = true)
    open fun getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    ): List<OrderApprovalOutboxMessage>? {
        return approvalOutboxRepository.findByTypeAndOutboxStatus(
            SagaConstants.ORDER_SAGA_NAME,
            outboxStatus,
            *sagaStatus
        )
    }

    @Transactional(readOnly = true)
    open fun getApprovalOutboxMessageBySagaIdAndSagaStatus(
        sagaId: UUID,
        vararg sagaStatus: SagaStatus
    ): OrderApprovalOutboxMessage? {
        return approvalOutboxRepository.findByTypeAndSagaIdAndSagaStatus(
            SagaConstants.ORDER_SAGA_NAME,
            sagaId,
            *sagaStatus
        )
    }

    @Transactional
    open fun save(orderApprovalOutboxMessage: OrderApprovalOutboxMessage) {
        val response = approvalOutboxRepository.save(orderApprovalOutboxMessage)

        if (response == null) {
            logger.error { "Could not save OrderApprovalOutboxMessage with outbox id: ${orderApprovalOutboxMessage.id}" }
            throw OrderDomainException("Could not save OrderApprovalOutboxMessage with outbox id: ${orderApprovalOutboxMessage.id}")
        }

        logger.info { "OrderApprovalOutboxMessage is saved with outbox id: ${orderApprovalOutboxMessage.id}" }
    }

    @Transactional
    open fun deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(
        outboxStatus: OutboxStatus,
        vararg sagaStatus: SagaStatus
    ) {
        approvalOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(
            SagaConstants.ORDER_SAGA_NAME,
            outboxStatus,
            *sagaStatus
        )
    }

    @Transactional
    open fun saveApprovalOutboxMessage(
        orderApprovalEventPayload: OrderApprovalEventPayload,
        orderStatus: OrderStatus,
        sagaStatus: SagaStatus,
        outboxStatus: OutboxStatus,
        sagaId: UUID
    ) {
        save(
            OrderApprovalOutboxMessage(
                id = UUID.randomUUID(),
                sagaId = sagaId,
                createdAt = orderApprovalEventPayload.createdAt,
                type = SagaConstants.ORDER_SAGA_NAME,
                payload = createPayload(orderApprovalEventPayload),
                orderStatus = orderStatus,
                sagaStatus = sagaStatus,
                outboxStatus = outboxStatus
            )
        )
    }

    private fun createPayload(orderApprovalEventPayload: OrderApprovalEventPayload): String? {
        try {
            return objectMapper.writeValueAsString(orderApprovalEventPayload)
        } catch (e: JsonProcessingException) {
            logger.error { "Could not convert order approval event payload to json: ${orderApprovalEventPayload.orderId}" }
            throw OrderDomainException("Could not convert order approval event payload to json: ${orderApprovalEventPayload.orderId}")
        }
    }
}