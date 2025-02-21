package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.RestaurantRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
open class OrderCreateHelper(
    val orderDomainService: com.food.ordering.system.kotlin.order.service.domain.OrderDomainService,
    val orderRepository: OrderRepository,
    val restaurantRepository: RestaurantRepository,
    val customerRepository: com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.CustomerRepository,
    val orderDataMapper: com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper,
    val orderCreatedPaymentRequestMessagePublisher: OrderCreatedPaymentRequestMessagePublisher
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun persistOrder(createOrderCommand: CreateOrderCommand): com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent {
        checkCustomer(createOrderCommand.customerId)
        val restaurant: Restaurant = checkRestaurant(createOrderCommand)
        val order: Order = orderDataMapper.createOrderCommandToOrder(createOrderCommand)
        val orderCreatedEvent: com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent =
            orderDomainService.validateAndInitiateOrderMethod(
                order,
                restaurant,
                orderCreatedPaymentRequestMessagePublisher
            )
        save(order)
        logger.info { "Order is created with id ${order.id}" }
        return orderCreatedEvent
    }

    private fun checkCustomer(customerId: UUID) {
        val customer: Optional<Customer> = customerRepository.findCustomer(customerId)

        if (customer.isEmpty) {
            logger.warn { "Could not found customer id with $customerId" }
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Could not found customer id with $customerId")
        }
    }

    private fun checkRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        val restaurant: Restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)
        val optionalRestaurant: Optional<Restaurant> = restaurantRepository.findRestaurantInformation(restaurant)

        return if (optionalRestaurant.isEmpty) {
            logger.warn { "Could not found restaurant with restaurant id  ${createOrderCommand.restaurantId}" }
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Could not found restaurant with restaurant id ${createOrderCommand.restaurantId}")
        } else {
            optionalRestaurant.get()
        }
    }

    private fun save(order: Order): Order {
        val orderResult: Order = orderRepository.save(order)

        if (orderResult == null) {
            logger.error { "Could not save order with id ${order.id}" }
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Could not save order with id ${order.id}")
        }
        logger.info { "Order is saved with id ${orderResult.id}" }
        return orderResult
    }
}