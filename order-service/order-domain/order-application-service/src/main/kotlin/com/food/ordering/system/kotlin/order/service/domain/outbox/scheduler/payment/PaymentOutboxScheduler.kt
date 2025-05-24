package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment

import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment.PaymentRequestMessagePublisher
import com.food.ordering.system.kotlin.outbox.OutboxScheduler
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class PaymentOutboxScheduler(
    val paymentOutboxHelper: PaymentOutboxHelper,
    val paymentRequestMessagePublisher: PaymentRequestMessagePublisher,
) : OutboxScheduler {
    private val logger = KotlinLogging.logger {}

    @Transactional
    @Scheduled(
        fixedDelayString = "\${order-service.outbox-scheduler-fixed-rate}",
        initialDelayString = "\${order-service.outbox-scheduler-initial-delay}"
    )
    override fun processOutboxMessage() {
        val outboxMessageResponse = paymentOutboxHelper.getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus.STARTED, SagaStatus.STARTED,
            SagaStatus.COMPENSATING
        )

        if (outboxMessageResponse != null && outboxMessageResponse.isNotEmpty()) {
            val outboxMessages = outboxMessageResponse
            logger.info {}
            "Received ${outboxMessages.size} OrderPaymentOutboxMessage with ids: ${
                outboxMessages.joinToString(",") { it.id.toString() }
            }, sending to message bus!"

            outboxMessages.forEach {
                paymentRequestMessagePublisher.publish(it, this::updateOutboxStatus)
            }

            logger.info { "${outboxMessages.size} OrderPaymentOutboxMessage sent to message bus!" }
        }
    }

    private fun updateOutboxStatus(orderPaymentOutboxMessage: OrderPaymentOutboxMessage, outboxStatus: OutboxStatus) {
        orderPaymentOutboxMessage.outboxStatus = outboxStatus
        paymentOutboxHelper.save(orderPaymentOutboxMessage)
        logger.info { "OrderPaymentOutboxMessage is updated with outbox status: ${outboxStatus.name}" }
    }
}