package com.food.ordering.system.kotlin.order.service.messaging.publisher.kafka

import com.food.ordering.system.kotlin.kafka.order.avro.model.PaymentRequestAvroModel
import com.food.ordering.system.kotlin.kafka.producer.service.KafkaProducer
import com.food.ordering.system.kotlin.order.service.domain.config.OrderServiceConfigData
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.kotlin.order.service.messaging.mapper.OrderMessagingDataMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

@Component
open class CreateOrderKafkaMessagePublisher(
    val orderMessagingDataMapper: OrderMessagingDataMapper,
    val orderServiceConfigData: OrderServiceConfigData,
    val kafkaProducer: KafkaProducer<String, PaymentRequestAvroModel>,
    val orderKafkaMessageHelper: OrderKafkaMessageHelper
) : OrderCreatedPaymentRequestMessagePublisher {

    private val logger = KotlinLogging.logger {}

    override fun publish(domainEvent: OrderCreatedEvent) {
        val orderId = domainEvent.order.id!!.value.toString()
        logger.info { "Received orderCreatedEvent for order id $orderId" }

        try {

            val paymentRequestAvroModel =
                orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent)
            kafkaProducer.send(
                orderServiceConfigData.paymentResponseTopicName,
                orderId,
                paymentRequestAvroModel,
                orderKafkaMessageHelper.getKafkaCallback(
                    orderServiceConfigData.paymentResponseTopicName,
                    paymentRequestAvroModel,
                    orderId,
                    "PaymentRequestAvroModel"
                )
            )

            logger.info { "PaymentRequestAvroModel sent to kafka for order id $orderId" }
        } catch (e: Exception) {
            logger.error { "Error while sending PaymentRequestAvroModel message to kafka with order id $orderId, error: ${e.message}" }
        }

    }
}