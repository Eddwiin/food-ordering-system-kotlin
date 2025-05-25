package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga.SagaStep
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import com.food.ordering.system.kotlin.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.approval.ApprovalOutboxHelper
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.outbox.OutboxStatus
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
open class OrderPaymentSaga(
    val orderDomainService: OrderDomainService,
    val orderRepository: OrderRepository,
    val paymentOutboxHelper: PaymentOutboxHelper,
    val approvalOutboxHelper: ApprovalOutboxHelper,
    val orderSagaHelper: OrderSagaHelper,
    val orderDataMapper: OrderDataMapper,
) : SagaStep<PaymentResponse> {
    private val logger = KotlinLogging.logger { }
    private val UTC = "UTC"

    @Transactional
    override fun process(paymentResponse: PaymentResponse) {
        val orderPaymentOutboxMessageResponse = paymentOutboxHelper.getPaymentOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(paymentResponse.sagaId), SagaStatus.STARTED
        )

        if (orderPaymentOutboxMessageResponse == null) {
            logger.info { "An outbox message with saga id: ${paymentResponse.sagaId} is already processed!" }
            return
        }

        val domainEvent = completePaymentForOrder(paymentResponse)
        val sagaStatus = orderSagaHelper.orderStatusToSagaStatus(domainEvent.order.orderStatus!!)

        paymentOutboxHelper.save(
            getUpdatedPaymentOutboxMessage(
                orderPaymentOutboxMessageResponse,
                domainEvent.order.orderStatus!!,
                sagaStatus
            )
        )

        approvalOutboxHelper.saveApprovalOutboxMessage(
            orderDataMapper.orderPaidEventToOrderApprovalEventPayload(
                domainEvent
            ),
            domainEvent.order.orderStatus!!,
            sagaStatus,
            OutboxStatus.STARTED,
            UUID.fromString(paymentResponse.sagaId)
        )

        logger.info { "Order with id: ${domainEvent.order.id!!.value} is paid successfully!" }
    }


    @Transactional
    override fun rollback(paymentResponse: PaymentResponse) {

        val orderPaymentOutboxMessageResponse = paymentOutboxHelper.getPaymentOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(paymentResponse.sagaId),
            *getCurrentSagaStatus(paymentResponse.paymentStatus)
        )

        if (orderPaymentOutboxMessageResponse == null) {
            logger.info { "An outbox message with saga id: ${paymentResponse.sagaId} is already rollback!" }
            return
        }

        val order = rollbackPaymentForOrder(paymentResponse)
        val sagaStatus = orderSagaHelper.orderStatusToSagaStatus(order.orderStatus!!)
        paymentOutboxHelper.save(
            getUpdatedPaymentOutboxMessage(
                orderPaymentOutboxMessageResponse,
                order.orderStatus!!,
                sagaStatus
            )
        )

        if (paymentResponse.paymentStatus == PaymentStatus.CANCELLED) {
            approvalOutboxHelper.save(getUpdatedApprovalOutboxMessage(orderPaymentOutboxMessageResponse, sagaStatus))
        }

        logger.info { "Order with id: ${order.id!!.value} is cancelled successfully!" }

    }

    private fun findOrder(orderId: String): Order {
        return orderSagaHelper.findOrder(orderId)
    }

    private fun getUpdatedPaymentOutboxMessage(
        orderPaymentOutboxMessageResponse: OrderPaymentOutboxMessage,
        orderStatus: OrderStatus,
        sagaStatus: SagaStatus
    ): OrderPaymentOutboxMessage {
        orderPaymentOutboxMessageResponse.processedAt = ZonedDateTime.now(ZoneId.of(UTC))
        orderPaymentOutboxMessageResponse.orderStatus = orderStatus
        orderPaymentOutboxMessageResponse.sagaStatus = sagaStatus
        return orderPaymentOutboxMessageResponse
    }

    private fun completePaymentForOrder(paymentResponse: PaymentResponse): OrderPaidEvent {
        logger.info { "Completing payment for order with id: ${paymentResponse.orderId}" }
        val order = findOrder(paymentResponse.orderId)
        val domainEvent = orderDomainService.payOrder(order)
        orderRepository.save(order)
        return domainEvent
    }

    private fun getCurrentSagaStatus(paymentStatus: PaymentStatus): Array<SagaStatus> {
        return when (paymentStatus) {
            PaymentStatus.COMPLETED -> arrayOf(SagaStatus.STARTED)
            PaymentStatus.CANCELLED -> arrayOf(SagaStatus.PROCESSING)
            PaymentStatus.FAILED -> arrayOf(SagaStatus.STARTED, SagaStatus.PROCESSING)
        }
    }


    private fun rollbackPaymentForOrder(paymentResponse: PaymentResponse): Order {
        logger.info { "Cancelling order with id: ${paymentResponse.orderId}" }
        val order = findOrder(paymentResponse.orderId)
        orderDomainService.cancelOrderPayment(order, paymentResponse.failureMessages)
        orderSagaHelper.saveOrder(order)
        return order
    }

    private fun getUpdatedApprovalOutboxMessage(
        sagaId: String,
        orderStatus: OrderStatus,
        sagaStatus: SagaStatus
    ): OrderApprovalOutboxMessage {
        val orderApprovalOutboxMessageResponse = approvalOutboxHelper.getApprovalOutboxMessageBySagaIdAndSagaStatus(
            UUID.fromString(sagaId),
            SagaStatus.COMPENSATING
        )

        if (orderApprovalOutboxMessageResponse == null) {
            throw OrderDomainException("Approval outbox message could not be found in ${SagaStatus.COMPENSATING.name} status for saga id: $sagaId")
        }

        return orderApprovalOutboxMessageResponse.copy(
            processedAt = ZonedDateTime.now(ZoneId.of(UTC)),
            orderStatus = orderStatus,
            sagaStatus = sagaStatus
        )

    }
}
