package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.saga.SagaStatus
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderSagaHelper(
    val orderRepository: OrderRepository
) {
    private val logger = KotlinLogging.logger {}

    fun findOrder(orderId: String): Order {
        val orderResponse = orderRepository.findById(OrderId(UUID.fromString(orderId)))
        if (orderResponse == null) {
            logger.error { "Order with id: $orderId could not be found!" }
            throw OrderNotFoundException("Order with id: $orderId could not be found!")
        }
        return orderResponse
    }

    fun saveOrder(order: Order) {
        orderRepository.save(order)
    }

    fun orderStatusToSagaStatus(orderStatus: OrderStatus): SagaStatus {
        return when (orderStatus) {
            OrderStatus.PAID -> SagaStatus.PROCESSING
            OrderStatus.APPROVED -> SagaStatus.SUCCEEDED
            OrderStatus.CANCELLING -> SagaStatus.COMPENSATING
            OrderStatus.CANCELLED -> SagaStatus.COMPENSATED
            else -> SagaStatus.STARTED
        }
    }
}