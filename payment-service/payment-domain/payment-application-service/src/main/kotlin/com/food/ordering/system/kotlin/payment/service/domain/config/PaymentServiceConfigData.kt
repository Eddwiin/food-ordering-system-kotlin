package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "payment")
open class PaymentServiceConfigData(
    val paymentRequestTopicName: String? = null,
    val paymentResponseTopicName: String? = null,
)