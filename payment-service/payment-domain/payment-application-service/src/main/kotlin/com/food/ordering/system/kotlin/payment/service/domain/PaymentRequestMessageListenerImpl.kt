package com.food.ordering.system.kotlin.payment.service.domain

import com.food.ordering.system.kotlin.payment.service.domain.core.event.PaymentEvent
import com.food.ordering.system.kotlin.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.kotlin.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service


@Service
class PaymentRequestMessageListenerImpl(
    val paymentRequestHelper: PaymentRequestHelper? = null
) : PaymentRequestMessageListener {
    private val logger = KotlinLogging.logger {};

    override fun completePayment(paymentRequest: PaymentRequest) {
        val paymentEvent = paymentRequestHelper!!.persistPayment(paymentRequest)
        fireEvent(paymentEvent)
    }

    override fun cancelPayment(paymentRequest: PaymentRequest) {
        val paymentEvent: PaymentEvent? = paymentRequestHelper?.persistCancelPayment(paymentRequest)
        fireEvent(paymentEvent!!)
    }

    private fun fireEvent(paymentEvent: PaymentEvent) {
        logger.info { "Publishing payment event with payment id: ${paymentEvent.payment?.id!!.value} and order id: ${paymentEvent.payment!!.orderId.value}" }
        paymentEvent.fire()
    }
}