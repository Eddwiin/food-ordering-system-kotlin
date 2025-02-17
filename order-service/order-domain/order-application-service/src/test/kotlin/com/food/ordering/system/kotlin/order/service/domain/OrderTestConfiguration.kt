package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.sytem.kotlin.order.service.domain.OrderDomainService
import com.food.ordering.sytem.kotlin.order.service.domain.OrderDomainServiceImpl
import com.food.ordering.sytem.kotlin.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.sytem.kotlin.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.sytem.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.sytem.kotlin.order.service.domain.ports.output.repository.RestaurantRepository
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication(scanBasePackages = ["com.food.ordering.system.kotlin.order"])
open class OrderTestConfiguration {
    @Bean
    open fun orderCreatedPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    open fun orderCancelledPaymentRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    open fun orderPaidRestaurantRequestMessagePublisher(): OrderCreatedPaymentRequestMessagePublisher {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher::class.java)
    }

    @Bean
    open fun orderRepository(): OrderRepository {
        return Mockito.mock(OrderRepository::class.java)
    }

    @Bean
    open fun customerRepository(): CustomerRepository {
        return Mockito.mock(CustomerRepository::class.java)
    }

    @Bean
    open fun restaurantRepository(): RestaurantRepository {
        return Mockito.mock(RestaurantRepository::class.java)
    }

    @Bean
    open fun orderDomainService(): OrderDomainService {
        return OrderDomainServiceImpl();
    }
}