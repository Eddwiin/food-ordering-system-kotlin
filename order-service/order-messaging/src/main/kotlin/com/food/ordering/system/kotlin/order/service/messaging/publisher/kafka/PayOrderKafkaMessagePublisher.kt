package com.food.ordering.system.kotlin.order.service.messaging.publisher.kafka

import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kotlin.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kotlin.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent
import com.food.ordering.system.kotlin.order.service.messaging.mapper.OrderMessagingDataMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
class PayOrderKafkaMessagePublisher(
    val orderMessagingDataMapper: OrderMessagingDataMapper,
    val orderServiceConfigData: OrderServiceConfigData,
    val kafkaProducer: KafkaProducer<String, RestaurantApprovalRequestAvroModel>,
    val orderKafkaMessageHelper: OrderKafkaMessageHelper
) : OrderPaidRestaurantRequestMessagePublisher {
    private val logger = KotlinLogging.logger {}

    override fun publish(domainEvent: OrderPaidEvent) {
        val orderId = domainEvent.order.id!!.value.toString()

        try {
            val restaurantApprovalRequestAvroModel =
                orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent)

            kafkaProducer.send(
                orderServiceConfigData.restaurantApprovalRequestTopicName,
                orderId,
                restaurantApprovalRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.restaurantApprovalRequestTopicName,
                    restaurantApprovalRequestAvroModel,
                    orderId,
                    "RestaurantApprovalRequestAvroModel"
                )
            )

            logger.info { "RestaurantApprovalRequestAvroModel sent to kafka for order id $orderId" }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}