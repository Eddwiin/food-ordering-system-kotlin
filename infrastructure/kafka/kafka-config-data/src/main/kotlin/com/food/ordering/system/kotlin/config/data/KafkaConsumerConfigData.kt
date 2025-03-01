package com.food.ordering.system.kotlin.config.data

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
open class KafkaConsumerConfigData(
    var keyDeserializer: String = "",
    var valueDeserializer: String = "",
    var autoOffsetReset: String = "",
    var specificAvroReaderKey: String = "",
    var specificAvroReader: String = "",
    var batchListener: Boolean = false,
    var autoStartup: Boolean = false,
    var concurrencyLevel: Int = 0,
    var sessionTimeoutMs: Int = 0,
    var heartbeatIntervalMs: Int = 0,
    var maxPollIntervalMs: Int = 0,
    var pollTimeoutMs: Long = 0,
    var maxPollRecords: Int = 0,
    var maxPartitionFetchBytesDefault: Int = 0,
    var maxPartitionFetchBytesBoostFactor: Int = 0
)
