package com.food.ordering.system.kotlin.restaurant.service.domain

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories(
    "com.food.ordering.system.kotlin.restaurant.service.dataaccess",
    "com.food.ordering.system.kotlin.dataaccess"
)
@EntityScan(
    "com.food.ordering.system.kotlin.restaurant.service.dataaccess",
    "com.food.ordering.system.kotlin.dataaccess"
)
@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin"])
open class RestaurantServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(RestaurantServiceApplication::class.java, *args)
        }
    }
}