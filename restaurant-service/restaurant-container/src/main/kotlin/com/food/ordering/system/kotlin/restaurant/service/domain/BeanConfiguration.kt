package com.food.ordering.system.kotlin.restaurant.service.domain

import RestaurantDomainImplService
import RestaurantDomainService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeanConfiguration {
    @Bean
    open fun restaurantDomainService(): RestaurantDomainService {
        return RestaurantDomainImplService()
    }
}