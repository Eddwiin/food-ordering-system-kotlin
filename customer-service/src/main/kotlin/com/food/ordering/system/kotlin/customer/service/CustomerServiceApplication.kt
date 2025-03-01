package com.food.ordering.system.kotlin.customer.service

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin.customer.service"])
open class CustomerServiceApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            org.springframework.boot.SpringApplication.run(CustomerServiceApplication::class.java, *args)
        }
    }
}