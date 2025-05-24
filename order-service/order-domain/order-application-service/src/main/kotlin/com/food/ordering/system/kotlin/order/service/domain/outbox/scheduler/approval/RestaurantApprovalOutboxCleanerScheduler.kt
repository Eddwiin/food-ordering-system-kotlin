package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval

import com.food.ordering.system.kotlin.outbox.OutboxScheduler
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled

class RestaurantApprovalOutboxCleanerScheduler(
    val approvalOutboxHelper: ApprovalOutboxHelper
) : OutboxScheduler {
    private val logger = KotlinLogging.logger {}

    @Scheduled(cron = "@midnight")
    override fun processOutboxMessage() {
        val outboxResponse = approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus.COMPLETED, SagaStatus.SUCCEEDED,
            SagaStatus.FAILED, SagaStatus.COMPENSATED
        )

        if (!outboxResponse.isNullOrEmpty()) {
            val outboxMessages = outboxResponse
            logger.info {
                "Received ${outboxMessages.size} OrderApprovalOutboxMessage for clean-up. The payload: ${
                    outboxMessages.joinToString(
                        "\n"
                    ) { it.payload }
                }"
            }
        }
    }
}