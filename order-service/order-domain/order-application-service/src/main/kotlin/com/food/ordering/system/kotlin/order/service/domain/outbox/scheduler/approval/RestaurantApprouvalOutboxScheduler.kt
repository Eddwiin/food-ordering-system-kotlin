package com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval

import com.food.ordering.system.kotlin.order.service.domain.OrderApprovalSaga
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.restaurantapproval.RestaurantApprovalRequestMessagePublisher
import com.food.ordering.system.kotlin.outbox.OutboxScheduler
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class RestaurantApprouvalOutboxScheduler(
    val approvalOutboxHelper: ApprovalOutboxHelper,
    val restaurantApprovalRequestMessagePublisher: RestaurantApprovalRequestMessagePublisher,
    private val orderApprovalSaga: OrderApprovalSaga
) : OutboxScheduler {
    private val logger = KotlinLogging.logger { }

    @Transactional
    @Scheduled(
        fixedDelayString = "\${order-service.outbox-scheduler-fixed-rate}",
        initialDelayString = "\${order-service.outbox-scheduler-initial-delay}"
    )
    override fun processOutboxMessage() {
        val outboxMessageResponse = approvalOutboxHelper.getApprovalOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus.STARTED, SagaStatus.STARTED,
            SagaStatus.COMPENSATING
        )

        if (!outboxMessageResponse.isNullOrEmpty()) {
            val outboxMessages = outboxMessageResponse
            logger.info {
                "Received ${outboxMessages.size} OrderApprovalOutboxMessage with ids: ${
                    outboxMessages.joinToString(",") { it.id.toString() }
                }, sending to message bus!"
            }

            outboxMessages.forEach {
                restaurantApprovalRequestMessagePublisher.publish(
                    it,
                    this::updateOutboxStatus
                )
            }
        }
    }

    private fun updateOutboxStatus(orderApprovalOutboxMessage: OrderApprovalOutboxMessage, outboxStatus: OutboxStatus) {
        orderApprovalOutboxMessage.outboxStatus = outboxStatus
        approvalOutboxHelper.save(orderApprovalOutboxMessage)
        logger.info { "OrderApprovalOutboxMessage is updated with outbox status: ${outboxStatus.name}" }
    }
}
