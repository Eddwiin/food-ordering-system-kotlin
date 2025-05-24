package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval

import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
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
    val approvalOutboxRepository: ApprovalOutboxRepository
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
}