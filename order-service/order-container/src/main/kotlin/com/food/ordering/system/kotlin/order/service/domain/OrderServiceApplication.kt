package com.food.ordering.system.kotlin.order.service.domain

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(basePackages = ["com.food.ordering.system.kotlin.order.service.dataaccess"])
@EntityScan(basePackages = ["com.food.ordering.system.kotlin.order.service.dataaccess"])
@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin"])
open class OrderServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(OrderServiceApplication::class.java, *args)
        }
    }
}