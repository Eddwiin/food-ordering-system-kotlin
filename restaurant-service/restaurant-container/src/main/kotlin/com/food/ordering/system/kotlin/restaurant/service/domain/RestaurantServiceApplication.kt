package com.food.ordering.system.kotlin.restaurant.service.domain

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin"])
open class RestaurantServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(RestaurantServiceApplication::class.java, *args)
        }
    }
}