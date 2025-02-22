package com.food.ordering.system.kotlin.config.data

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
open class KafkaConfigData {
    var bootstrapServers: String = ""
    var schemaRegistryUrlKey: String = ""
    var schemaRegistryUrl: String = ""
    var numOfPartitions: Int = 1
    var replicationFactor: Short = 1
}