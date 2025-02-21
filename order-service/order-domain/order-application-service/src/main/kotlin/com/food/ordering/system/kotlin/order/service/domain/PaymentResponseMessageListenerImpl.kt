package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.message.PaymentResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Validated
@Service
class PaymentResponseMessageListenerImpl :
    com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.payment.PaymentResponseListener {
    private val logger = KotlinLogging.logger {}

    override fun paymentCompleted(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }

    override fun paymentCancelled(paymentResponse: PaymentResponse) {
        TODO("Not yet implemented")
    }
}