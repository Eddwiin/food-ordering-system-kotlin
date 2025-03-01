package com.food.ordering.system.kotlin.kafka.producer.service.impl

import com.food.ordering.system.kotlin.kafka.producer.exception.KafkaProducerException
import com.food.ordering.system.kotlin.kafka.producer.service.KafkaProducer
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PreDestroy
import org.apache.avro.specific.SpecificRecordBase
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.concurrent.CompletableFuture

@Component
open class KafkaProducerImpl<K : Serializable, V : SpecificRecordBase>(
    val kafkaTemplate: KafkaTemplate<K, V>
) : KafkaProducer<K, V> {
    private val logger = KotlinLogging.logger {}

    override fun send(topicName: String, key: K, message: V, callback: CompletableFuture<SendResult<K, V>>) {
        logger.info { "Sending message=${message} to topic=${key}" }
        try {
            val kafkaResultFuture = kafkaTemplate.send(topicName, key, message)
            kafkaResultFuture.thenAccept(callback::complete)
        } catch (exception: Exception) {
            logger.error { "Error with kafka producer with key: $key, message: $message, exception: ${exception.message}" }
            throw KafkaProducerException("Error on kafka producer with key: $key and message: $message")
        }
    }

    @PreDestroy
    fun close() {
        if (kafkaTemplate != null) {
            logger.info { "Closing Kafka producer!" }
            kafkaTemplate.destroy()
        }
    }
}