package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga.SagaStep
import com.food.ordering.system.kotlin.domain.DomainConstants.Companion.UTC
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval.ApprovalOutboxHelper
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
open class OrderApprovalSaga(
    val orderDomainService: OrderDomainService,
    val orderSagaHelper: OrderSagaHelper,
    val paymentOutboxHelper: PaymentOutboxHelper,
    val approvalOutboxHelper: ApprovalOutboxHelper,
    val orderDataMapper: OrderDataMapper,
) : SagaStep<RestaurantApprovalResponse> {
    private val logger = KotlinLogging.logger {}

    @Transactional
    override fun process(restaurantApprovalResponse: RestaurantApprovalResponse) {
        val orderApprovalOutboxMessage = approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(restaurantApprovalResponse.sagaId), SagaStatus.PROCESSING
        )

        if (orderApprovalOutboxMessage == null) {
            logger.info { "An outbox message with saga id: ${restaurantApprovalResponse.sagaId} is already processed!" }
            return
        }

        val order = approveOrder(restaurantApprovalResponse)
        val sagaStatus = orderSagaHelper.orderStatusToSagaStatus(order.orderStatus!!)

        approvalOutboxHelper.save(
            getUpdatedApprovalOutboxMessage(
                orderApprovalOutboxMessage, order.orderStatus!!, sagaStatus
            )
        )

        paymentOutboxHelper.save(
            getUpdatedPaymentOutboxMessage(
                restaurantApprovalResponse.sagaId, order.orderStatus!!, sagaStatus
            )
        )

        logger.info { "Order with id: ${order.id!!.value} is approved successfully!" }
    }

    @Transactional
    override fun rollback(restaurantApprovalResponse: RestaurantApprovalResponse) {

        val orderApprovalOutboxMessageResponse = approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(restaurantApprovalResponse.sagaId), SagaStatus.PROCESSING
        )

        if (orderApprovalOutboxMessageResponse == null) {
            logger.info { "An outbox message with saga id: ${restaurantApprovalResponse.sagaId} is already roll backed!" }
            return;
        }

        val domainEvent = rollbackOrder(restaurantApprovalResponse)
        val sagaStatus = orderSagaHelper.orderStatusToSagaStatus(domainEvent.order.orderStatus!!)
        approvalOutboxHelper.save(
            getUpdatedApprovalOutboxMessage(
                orderApprovalOutboxMessageResponse,
                domainEvent.order.orderStatus!!,
                sagaStatus
            )
        )

        paymentOutboxHelper.savePaymentOutboxMessage(
            orderDataMapper.orderCancelledEventToOrderPaymentEventPayload(
                domainEvent
            ),
            domainEvent.order.orderStatus!!,
            sagaStatus,
            OutboxStatus.STARTED,
            UUID.fromString(restaurantApprovalResponse.sagaId)
        )
 
        logger.info { "Order with id: ${domainEvent.order.id!!.value} is cancelled successfully!" }
    }

    private fun approveOrder(restaurantApprovalResponse: RestaurantApprovalResponse): Order {
        logger.info { "Approving order with id: ${restaurantApprovalResponse.restaurantId}" }
        val order = orderSagaHelper.findOrder(restaurantApprovalResponse.orderId)
        orderDomainService.approvedOrder(order)
        orderSagaHelper.saveOrder(order)
        return order;
    }

    private fun getUpdatedApprovalOutboxMessage(
        orderApprovalOutboxMessage: OrderApprovalOutboxMessage, orderStatus: OrderStatus, sagaStatus: SagaStatus
    ): OrderApprovalOutboxMessage {
        return orderApprovalOutboxMessage.copy(
            processedAt = ZonedDateTime.now(ZoneId.of(UTC)), orderStatus = orderStatus, sagaStatus = sagaStatus
        )
    }

    private fun getUpdatedPaymentOutboxMessage(
        sagaId: String, orderStatus: OrderStatus, sagaStatus: SagaStatus
    ): OrderPaymentOutboxMessage {
        val orderPaymentOutboxMessageResponse = paymentOutboxHelper.getPaymentOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(sagaId), SagaStatus.PROCESSING
        )

        if (orderPaymentOutboxMessageResponse == null) {
            throw OrderDomainException("Payment outbox message could not be found in ${SagaStatus.PROCESSING.name} status for saga id: $sagaId")
        }

        return orderPaymentOutboxMessageResponse.copy(
            processedAt = ZonedDateTime.now(ZoneId.of(UTC)), orderStatus = orderStatus, sagaStatus = sagaStatus
        )
    }

    private fun rollbackOrder(restaurantApprovalResponse: RestaurantApprovalResponse): OrderCancelledEvent {
        logger.info { "Cancelling order with id: ${restaurantApprovalResponse.restaurantId}" }
        val order = orderSagaHelper.findOrder(restaurantApprovalResponse.orderId)
        val domainEvent = orderDomainService.cancelOrderPayment(order, restaurantApprovalResponse.failureMessages)
        orderSagaHelper.saveOrder(order)
        return domainEvent
    }
}
