package com.food.ordering.system.kotlin.com.food.ordering.system.payment.service.domain

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@EnableJpaRepositories(basePackages = ["com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess"])
@EntityScan(basePackages = ["com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess"])
@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin"])
open class PaymentServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(PaymentServiceApplication::class.java, *args)
        }
    }
}