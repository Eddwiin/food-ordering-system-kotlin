package com.food.ordering.system.kotlin.restaurant.service.domain.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "restaurant-service")
open class RestaurantServiceConfigData(
    val restaurantApprovalRequestTopicName: String? = null,
    val restaurantApprovalResponseTopicName: String? = null
)