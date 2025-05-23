package com.food.ordering.system.kotlin.config.data

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
open class KafkaConfigData {
    var bootstrapServers: String? = null
    var schemaRegistryUrlKey: String? = null
    var schemaRegistryUrl: String? = null
    var numOfPartitions: Int? = null
    var replicationFactor: Short? = null
}