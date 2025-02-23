package com.food.ordering.system.kotlin.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
open class KafkaConsumerConfigData(
    val keyDeserializer: String = "",
    val valueDeserializer: String = "",
    val autoOffsetReset: String = "",
    val specificAvroReaderKey: String = "",
    val specificAvroReader: String = "",
    val batchListener: Boolean = false,
    val autoStartup: Boolean = false,
    val concurrencyLevel: Int = 0,
    val sessionTimeoutMs: Int = 0,
    val heartbeatIntervalMs: Int = 0,
    val maxPollIntervalMs: Int = 0,
    val pollTimeoutMs: Long = 0,
    val maxPollRecords: Int = 0,
    val maxPartitionFetchBytesDefault: Int = 0,
    val maxPartitionFetchBytesBoostFactor: Int = 0
)
