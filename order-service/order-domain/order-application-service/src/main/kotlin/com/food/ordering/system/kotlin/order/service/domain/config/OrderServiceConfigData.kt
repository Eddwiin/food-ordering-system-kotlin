package com.food.ordering.system.kotlin.order.service.domain.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "order-service")
open class OrderServiceConfigData {
    lateinit var paymentRequestTopicName: String
    lateinit var paymentResponseTopicName: String
    lateinit var restaurantApprovalRequestTopicName: String
    lateinit var restaurantApprovalResponseTopicName: String

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderServiceConfigData

        if (paymentRequestTopicName != other.paymentRequestTopicName) return false
        if (paymentResponseTopicName != other.paymentResponseTopicName) return false
        if (restaurantApprovalRequestTopicName != other.restaurantApprovalRequestTopicName) return false
        if (restaurantApprovalResponseTopicName != other.restaurantApprovalResponseTopicName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = paymentRequestTopicName.hashCode()
        result = 31 * result + paymentResponseTopicName.hashCode()
        result = 31 * result + restaurantApprovalRequestTopicName.hashCode()
        result = 31 * result + restaurantApprovalResponseTopicName.hashCode()
        return result
    }


    class OrderServiceConfigDataBuilder {
        private var paymentRequestTopicName: String? = null
        private var paymentResponseTopicName: String? = null
        private var restaurantApprovalRequestTopicName: String? = null
        private var restaurantApprovalResponseTopicName: String? = null

        fun paymentRequestTopicName(paymentRequestTopicName: String) = apply {
            this.paymentRequestTopicName = paymentRequestTopicName
        }

        fun paymentResponseTopicName(paymentResponseTopicName: String) = apply {
            this.paymentResponseTopicName = paymentResponseTopicName
        }

        fun restaurantApprovalRequestTopicName(restaurantApprovalRequestTopicName: String) = apply {
            this.restaurantApprovalRequestTopicName = restaurantApprovalRequestTopicName
        }

        fun restaurantApprovalResponseTopicName(restaurantApprovalResponseTopicName: String) = apply {
            this.restaurantApprovalResponseTopicName = restaurantApprovalResponseTopicName
        }

        fun build(): OrderServiceConfigData {
            val configData = OrderServiceConfigData()
            configData.paymentRequestTopicName = paymentRequestTopicName
                ?: throw IllegalArgumentException("paymentRequestTopicName must not be null")
            configData.paymentResponseTopicName = paymentResponseTopicName
                ?: throw IllegalArgumentException("paymentResponseTopicName must not be null")
            configData.restaurantApprovalRequestTopicName = restaurantApprovalRequestTopicName
                ?: throw IllegalArgumentException("restaurantApprovalRequestTopicName must not be null")
            configData.restaurantApprovalResponseTopicName = restaurantApprovalResponseTopicName
                ?: throw IllegalArgumentException("restaurantApprovalResponseTopicName must not be null")
            return configData
        }
    }

    companion object {
        fun builder() = OrderServiceConfigDataBuilder()
    }
}