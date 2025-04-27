package com.food.ordering.system.kotlin.payment.service.domain.ports.input.message.listener

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.dto.PaymentRequest

interface PaymentRequestMessageListener {
    fun completePayment(paymentRequest: PaymentRequest);
    fun cancelPayment(paymentRequest: PaymentRequest);
}