package com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.restaurantapproval

import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import java.util.function.BiConsumer

interface RestaurantApprovalRequestMessagePublisher {
    fun publish(
        orderApprovalOutboxMessage: OrderApprovalOutboxMessage,
        outboxCallback: BiConsumer<OrderApprovalOutboxMessage, OutboxStatus>
    )
}