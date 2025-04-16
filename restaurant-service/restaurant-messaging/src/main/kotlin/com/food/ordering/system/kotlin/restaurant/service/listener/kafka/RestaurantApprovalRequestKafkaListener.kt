package com.food.ordering.system.kotlin.restaurant.service.listener.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener
import com.food.ordering.system.kotlin.restaurant.service.mapper.RestaurantMessagingDataMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RestaurantApprovalRequestKafkaListener(
    val restaurantApprovalRequestMessageListener: RestaurantApprovalRequestMessageListener? = null,
    val restaurantMessagingDataMapper: RestaurantMessagingDataMapper? = null
) : KafkaConsumer<RestaurantApprovalRequestAvroModel> {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["restaurant-service.restaurant-approval-request-topic-name"]
    )
    override fun receive(
        @Payload messages: List<RestaurantApprovalRequestAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info {
            "${messages.size} number of orders approval requests received with keys $keys, partitions $partitions and offsets $offsets, sending for restaurant approval"
        }

        messages.forEach { restaurantApprovalRequestAvroModel ->
            logger.info { "Processing order approval for order id: ${restaurantApprovalRequestAvroModel.orderId}" }
            restaurantApprovalRequestMessageListener?.approveOrder(
                restaurantMessagingDataMapper?.restaurantApprovalRequestAvroModelToRestaurantApproval(
                    restaurantApprovalRequestAvroModel
                )!!
            )
        }
    }
}