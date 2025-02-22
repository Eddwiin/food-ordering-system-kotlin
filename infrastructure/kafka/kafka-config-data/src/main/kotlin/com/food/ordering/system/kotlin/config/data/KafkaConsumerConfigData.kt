package com.food.ordering.system.kotlin.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
open class KafkaConsumerConfigData {
    private val keyDeserializer: String = ""
    private val valueDeserializer: String = ""
    private val autoOffsetReset: String = ""
    private val specificAvroReaderKey: String = ""
    private val specificAvroReader: String = ""
    private val batchListener: Boolean = false
    private val autoStartup: Boolean = false
    private val concurrencyLevel: Int = 0
    private val sessionTimeoutMs: Int = 0
    private val heartbeatIntervalMs: Int = 0
    private val maxPollIntervalMs: Int = 0
    private val pollTimeoutMs: Long = 0
    private val maxPollRecords: Int = 0
    private val maxPartitionFetchBytesDefault: Int = 0
    private val maxPartitionFetchBytesBoostFactor: Int = 0
}
