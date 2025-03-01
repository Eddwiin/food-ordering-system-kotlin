package com.food.ordering.system.kotlin.order.service.messaging.publisher.kafka

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class OrderKafkaMessageHelper {
    private val logger = KotlinLogging.logger {}

    fun <T> getKafkaCallback(
        responseTopicName: String,
        requestAvroModel: T,
        orderId: String,
        requestAvroModelName: String
    ): CompletableFuture<SendResult<String, T>> {
        val completableFuture = CompletableFuture<SendResult<String, T>>()

        completableFuture.whenComplete { result, exception ->
            if (exception != null) {

                logger.error { "Error while sending $requestAvroModelName message ${requestAvroModel.toString()} to topic $responseTopicName" }
                exception.printStackTrace()
            } else {
                val metadata = result.recordMetadata
                logger.info {
                    "Received successful response from Kafka for order id: $orderId " +
                            "Topic: ${metadata.topic()} Partition: ${metadata.partition()} Offset: ${metadata.offset()} Timestamp: ${metadata.timestamp()}"
                }
            }
        }

        return completableFuture
    }
}