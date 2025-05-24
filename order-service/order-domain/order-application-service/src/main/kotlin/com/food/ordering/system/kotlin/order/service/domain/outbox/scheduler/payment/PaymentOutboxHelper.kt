package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentEventPayload
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.PaymentOutboxRepository
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import com.food.ordering.system.kotlin.saga.order.SagaConstants
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
open class PaymentOutboxHelper(
    val paymentOutboxRepository: PaymentOutboxRepository, val objectMapper: ObjectMapper
) {
    private val logger = KotlinLogging.logger {}


    @Transactional(readOnly = true)
    open fun getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
        outboxStatus: OutboxStatus, vararg sagaStatus: SagaStatus
    ): List<OrderPaymentOutboxMessage>? {
        return paymentOutboxRepository.findByTypeAndOutboxStatus(
            SagaConstants.ORDER_SAGA_NAME, outboxStatus, *sagaStatus
        )
    }

    @Transactional(readOnly = true)
    open fun getPaymentOutboxMessageBySagaIdAndSagaStatus(
        sagaId: UUID, vararg sagaStatus: SagaStatus
    ): OrderPaymentOutboxMessage? {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(
            SagaConstants.ORDER_SAGA_NAME, sagaId, *sagaStatus
        )
    }

    @Transactional
    open fun save(orderPaymentOutboxMessage: OrderPaymentOutboxMessage) {
        val response = paymentOutboxRepository.save(orderPaymentOutboxMessage);

        if (response == null) {
            logger.error {
                "Could not save order payment outbox message with outbox id: " + "${orderPaymentOutboxMessage.id}"
            }
            throw OrderDomainException(
                "Could not save order payment outbox message with outbox id: " + "${orderPaymentOutboxMessage.id}"
            )
        }
    }

    fun savePaymentOutboxMessage(
        paymentEventPayload: OrderPaymentEventPayload,
        orderStatus: OrderStatus,
        sagaStatus: SagaStatus,
        outboxStatus: OutboxStatus,
        sagaId: UUID
    ) {
        save(
            OrderPaymentOutboxMessage(
                id = UUID.randomUUID(),
                sagaId = sagaId,
                createAt = paymentEventPayload.createdAt,
                type = SagaConstants.ORDER_SAGA_NAME,
                payload = createPayload(paymentEventPayload),
                orderStatus = orderStatus,
                sagaStatus = sagaStatus,
                outboxStatus = outboxStatus
            )
        )
    }

    private fun createPayload(paymentEventPayload: OrderPaymentEventPayload): String {
        try {
            return objectMapper.writeValueAsString(paymentEventPayload)
        } catch (e: JsonProcessingException) {
            logger.error { "Could not convert order payment event payload to json: ${paymentEventPayload.orderId}" }
            throw OrderDomainException(
                "Could not convert order payment event payload to json: ${paymentEventPayload.orderId}", e
            )
        }
    }

    fun deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(
        outboxStatus: OutboxStatus, vararg sagaStatus: SagaStatus
    ) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(
            SagaConstants.ORDER_SAGA_NAME, outboxStatus, *sagaStatus
        )
    }
}