package com.food.ordering.system.order.service.messaging.listener.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kotlin.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kotlin.order.service.domain.entity.Order.Companion.FAILURE_MESSAGE_DELIMITER
import com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener
import com.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class RestaurantApprovalResponseKafkaListener(
    val restaurantApprovalResponseMessageListener: RestaurantApprovalResponseMessageListener,
    val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        id = "\${kafka-consumer-config.restaurant-approval-consumer-group-id}",
        topics = ["order-service.restaurant-approval-response-topic-name"]
    )
    override fun receive(
        @Payload messages: List<RestaurantApprovalResponseAvroModel>,
        @Header(KafkaHeaders.KEY) keys: List<String>,
        @Header(KafkaHeaders.PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {

        logger.info { "${messages.size} number of restaurant approval responses received with keys $keys, partitions $partitions and offsets $offsets" }


        messages.forEach { restaurantApprovalResponseAvroModel ->
            when (restaurantApprovalResponseAvroModel.orderApprovalStatus) {
                OrderApprovalStatus.APPROVED -> {
                    logger.info { "Processing approved order for order id: ${restaurantApprovalResponseAvroModel.orderId}" }
                    restaurantApprovalResponseMessageListener.orderApproved(
                        orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(
                            restaurantApprovalResponseAvroModel
                        )
                    )
                }

                OrderApprovalStatus.REJECTED -> {
                    logger.info {
                        "Processing rejected order for order id: ${restaurantApprovalResponseAvroModel.orderId}, with failure messages: ${
                            restaurantApprovalResponseAvroModel.failureMessages.joinToString(
                                FAILURE_MESSAGE_DELIMITER
                            )
                        }"
                    }
                    restaurantApprovalResponseMessageListener.orderRejected(
                        orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(
                            restaurantApprovalResponseAvroModel
                        )
                    )
                }

                else -> {
                    logger.warn { "Unknown order approval status for order id: ${restaurantApprovalResponseAvroModel.orderId}" }
                }
            }
        }
    }
}