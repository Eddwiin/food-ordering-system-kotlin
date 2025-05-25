package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.kotlin.order.service.domain.entity.Order.Companion.FAILURE_MESSAGE_DELIMITER
import com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.payment.PaymentResponseListener
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
open class PaymentResponseMessageListenerImpl(
    val orderPaymentSaga: OrderPaymentSaga
) : PaymentResponseListener {
    private val logger = KotlinLogging.logger {}

    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        orderPaymentSaga.process(paymentResponse)
        logger.info { "Order payment Saga process operation is completed for order id: ${paymentResponse.orderId}" }
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        orderPaymentSaga.rollback(paymentResponse)
        logger.info { "Order is roll back with failure messages: ${paymentResponse.failureMessages.joinToString { FAILURE_MESSAGE_DELIMITER }}" }
    }
}