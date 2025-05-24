package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment

import com.food.ordering.system.kotlin.outbox.OutboxScheduler
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PaymentOutboxCleanerScheduler(private val paymentOutboxHelper: PaymentOutboxHelper) : OutboxScheduler {
    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "@midnight")
    override fun processOutboxMessage() {
        val outboxResponse = paymentOutboxHelper.getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus.COMPLETED,
            SagaStatus.SUCCEEDED,
            SagaStatus.FAILED,
            SagaStatus.COMPENSATED
        )

        if (outboxResponse != null && outboxResponse.isNotEmpty()) {
            val outboxMessages = outboxResponse
            logger.info {
                "Received ${outboxMessages.size} OrderPaymentOutboxMessage for clean-up. The payloads: ${
                    outboxMessages.joinToString("\n") { it.payload }
                }"
            }

            paymentOutboxHelper.deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(
                OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED
            )

            logger.info { "${outboxMessages.size} OrderPaymentOutboxMessage deleted from database!" }
        }
    }
}