package com.food.ordering.system.kotlin.kafka.producer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class KafkaMessageHelper {
    private val logger = KotlinLogging.logger {}

    fun <T> getKafkaCallback(
        responseTopicName: String,
        avroModel: T,
        orderId: String,
        avroModelName: String
    ): CompletableFuture<SendResult<String, T>> {
        return object : CompletableFuture<SendResult<String, T>>() {
            fun onFailure(ex: Throwable) {
                logger.error {
                    "Error while sending $avroModelName message ${avroModel.toString()} to topic $responseTopicName"
                }
            }

            fun onSuccess(result: SendResult<String, T>) {
                val metadata = result.recordMetadata
                logger.info {
                    "Received successful response from Kafka for order id: $orderId " +
                            "Topic: ${metadata.topic()} Partition: ${metadata.partition()} " +
                            "Offset: ${metadata.offset()} Timestamp: ${metadata.timestamp()}"
                }
            }
        }
    }
}