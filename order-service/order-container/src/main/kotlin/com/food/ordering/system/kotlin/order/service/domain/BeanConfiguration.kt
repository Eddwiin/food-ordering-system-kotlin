package com.food.ordering.system.kotlin.order.service.domain

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class BeanConfiguration {
    @Bean
    open fun orderDomainService(): OrderDomainService {
        return OrderDomainServiceImpl()
    }
}