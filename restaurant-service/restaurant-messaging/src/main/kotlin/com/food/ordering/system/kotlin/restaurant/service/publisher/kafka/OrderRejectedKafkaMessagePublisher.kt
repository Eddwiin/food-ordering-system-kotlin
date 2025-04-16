package com.food.ordering.system.kotlin.restaurant.service.publisher.kafka

import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kotlin.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.kotlin.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kotlin.restaurant.service.domain.config.RestaurantServiceConfigData
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher
import com.food.ordering.system.kotlin.restaurant.service.mapper.RestaurantMessagingDataMapper
import event.OrderRejectedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class OrderRejectedKafkaMessagePublisher(
    val restaurantMessagingDataMapper: RestaurantMessagingDataMapper? = null,
    val kafkaProducer: KafkaProducer<String, RestaurantApprovalResponseAvroModel>? = null,
    val restaurantServiceConfigData: RestaurantServiceConfigData? = null,
    val kafkaMessageHelper: KafkaMessageHelper? = null
) : OrderRejectedMessagePublisher {
    private val logger = KotlinLogging.logger {}

    override fun publish(domainEvent: OrderRejectedEvent) {
        val orderId = domainEvent.orderApproval?.orderId?.value.toString()

        logger.info { "Received OrderRejectedEvent for order id: $orderId" }

        try {
            val restaurantApprovalResponseAvroModel = restaurantMessagingDataMapper
                ?.orderRejectedEventToRestaurantApprovalResponseAvroModel(domainEvent)
                ?: throw IllegalArgumentException("Failed to map OrderRejectedEvent to RestaurantApprovalResponseAvroModel")

            kafkaProducer?.send(
                restaurantServiceConfigData?.restaurantApprovalResponseTopicName
                    ?: throw IllegalArgumentException("RestaurantApprovalResponseTopicName cannot be null"),
                orderId,
                restaurantApprovalResponseAvroModel,
                kafkaMessageHelper?.getKafkaCallback(
                    restaurantServiceConfigData.restaurantApprovalResponseTopicName!!,
                    restaurantApprovalResponseAvroModel,
                    orderId,
                    "RestaurantApprovalResponseAvroModel"
                ) ?: throw IllegalArgumentException("Kafka callback cannot be null")
            )

            logger.info { "RestaurantApprovalResponseAvroModel sent to Kafka at: ${System.nanoTime()}" }
        } catch (e: Exception) {
            logger.error {
                "Error while sending RestaurantApprovalResponseAvroModel message to Kafka with order id: $orderId, error: ${e.message}"
            }
        }
    }
}