package com.food.ordering.system.kafka.consumer.config

import com.food.ordering.system.kotlin.config.data.KafkaConfigData
import com.food.ordering.system.kotlin.config.data.KafkaConsumerConfigData
import org.apache.avro.specific.SpecificRecordBase
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import java.io.Serializable


@Configuration
open class KafkaConsumerConfig<K : Serializable, V : SpecificRecordBase>(
    val kafkaConfigData: KafkaConfigData,
    val kafkaConsumerConfigData: KafkaConsumerConfigData
) {
    @Bean
    open fun consumerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        kafkaConfigData.bootstrapServers?.let { props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, it) }
            ?: throw IllegalStateException("Bootstrap servers configuration is missing")
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = kafkaConsumerConfigData.keyDeserializer
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = kafkaConsumerConfigData.valueDeserializer
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = kafkaConsumerConfigData.autoOffsetReset
        kafkaConfigData.schemaRegistryUrl?.let {
            kafkaConfigData.schemaRegistryUrlKey?.let { it1 -> props.put(it1, it) }
                ?: throw IllegalStateException("Schema Registry URL KEY is missing")
        } ?: throw IllegalStateException("Schema Registry URL is missing")
        props[kafkaConsumerConfigData.specificAvroReaderKey] = kafkaConsumerConfigData.specificAvroReader
        props[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = kafkaConsumerConfigData.sessionTimeoutMs
        props[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] = kafkaConsumerConfigData.heartbeatIntervalMs
        props[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = kafkaConsumerConfigData.maxPollIntervalMs
        props[ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG] =
            kafkaConsumerConfigData.maxPartitionFetchBytesDefault *
                    kafkaConsumerConfigData.maxPartitionFetchBytesBoostFactor
        props[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = kafkaConsumerConfigData.maxPollRecords
        return props
    }

    @Bean
    open fun consumerFactory(): ConsumerFactory<K, V> {
        return DefaultKafkaConsumerFactory<K, V>(consumerConfigs())
    }

    open fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<K, V>> {
        val factory = ConcurrentKafkaListenerContainerFactory<K, V>();
        factory.setConsumerFactory(consumerFactory())
        factory.isBatchListener = kafkaConsumerConfigData.batchListener
        factory.setConcurrency(kafkaConsumerConfigData.concurrencyLevel)
        factory.setAutoStartup(kafkaConsumerConfigData.autoStartup)
        factory.containerProperties.pollTimeout = kafkaConsumerConfigData.pollTimeoutMs
        return factory;
    }
}