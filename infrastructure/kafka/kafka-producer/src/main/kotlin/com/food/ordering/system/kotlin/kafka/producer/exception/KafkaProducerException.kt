package com.food.ordering.system.kotlin.kafka.producer.exception

class KafkaProducerException(override val message: String) : RuntimeException(message)