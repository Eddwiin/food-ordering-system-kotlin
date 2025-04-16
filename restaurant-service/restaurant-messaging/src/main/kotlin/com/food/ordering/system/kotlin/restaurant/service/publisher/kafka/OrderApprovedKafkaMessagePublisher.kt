package com.food.ordering.system.kotlin.restaurant.service.publisher.kafka

import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kotlin.kafka.producer.KafkaMessageHelper
import com.food.ordering.system.kotlin.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kotlin.restaurant.service.domain.config.RestaurantServiceConfigData
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher
import com.food.ordering.system.kotlin.restaurant.service.mapper.RestaurantMessagingDataMapper
import event.OrderApprovedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class OrderApprovedKafkaMessagePublisher(
    val restaurantMessagingDataMapper: RestaurantMessagingDataMapper? = null,
    val kafkaProducer: KafkaProducer<String, RestaurantApprovalResponseAvroModel>? = null,
    val restaurantServiceConfigData: RestaurantServiceConfigData? = null,
    val kafkaMessageHelper: KafkaMessageHelper? = null
) : OrderApprovedMessagePublisher {
    private val logger = KotlinLogging.logger {}

    override fun publish(domainEvent: OrderApprovedEvent) {

        val orderId = domainEvent.orderApproval?.orderId?.value.toString()

        logger.info { "Received OrderApprovedEvent for order id: $orderId" }

        try {
            val restaurantApprovalResponseAvroModel = restaurantMessagingDataMapper
                ?.orderApprovedEventToRestaurantApprovalResponseAvroModel(domainEvent)

            kafkaProducer?.send(
                restaurantServiceConfigData?.restaurantApprovalResponseTopicName.orEmpty(),
                orderId,
                restaurantApprovalResponseAvroModel!!,
                kafkaMessageHelper?.getKafkaCallback(
                    restaurantServiceConfigData?.restaurantApprovalResponseTopicName.orEmpty(),
                    restaurantApprovalResponseAvroModel,
                    orderId,
                    "RestaurantApprovalResponseAvroModel"
                )!!
            )

            logger.info { "RestaurantApprovalResponseAvroModel sent to kafka at: ${System.nanoTime()}" }
        } catch (e: Exception) {
            logger.error {
                "Error while sending RestaurantApprovalResponseAvroModel message to kafka with order id: $orderId, error: ${e.message}"
            }
        }
    }
}