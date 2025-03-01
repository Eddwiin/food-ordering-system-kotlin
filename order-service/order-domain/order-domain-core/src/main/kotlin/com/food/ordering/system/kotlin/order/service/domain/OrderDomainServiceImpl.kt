package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Product
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.function.Consumer

class OrderDomainServiceImpl() : OrderDomainService {
    private val logger = KotlinLogging.logger {}
    private val UTC: String = "UTC"

    override fun validateAndInitiateOrderMethod(
        order: Order,
        restaurant: Restaurant,
        orderCreatedEventDomainEventPublisher: DomainEventPublisher<OrderCreatedEvent>
    ): OrderCreatedEvent {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant)
        order.validateOrder()
        order.initializeOrder()
        logger.info { "Order with id: ${order.id!!.value} is initiated" }
        return OrderCreatedEvent(
            order,
            ZonedDateTime.now(ZoneId.of(UTC)),
            orderCreatedEventDomainEventPublisher
        )

    }

    override fun payOrder(
        order: Order,
        orderPaidEventDomainEventPublisher: DomainEventPublisher<OrderPaidEvent>
    ): OrderPaidEvent {
        order.pay()
        logger.info { "Order with id: ${order.id!!.value} is paid" }
        return OrderPaidEvent(
            order,
            ZonedDateTime.now(ZoneId.of(UTC)),
            orderPaidEventDomainEventPublisher
        )
    }

    override fun approvedOrder(order: Order) {
        order.approve();
        logger.info { "Order with id: ${order.id!!.value} is approved" }
    }

    override fun cancelOrderPayment(
        order: Order,
        failureMessages: MutableList<String>,
        orderCancelledEventDomainEventPublisher: DomainEventPublisher<OrderCancelledEvent>
    ): OrderCancelledEvent {
        order.initCancel(failureMessages);
        logger.info { "Order payment is cancelling for order id: ${order.id!!.value}" }
        return OrderCancelledEvent(
            order, ZonedDateTime.now(ZoneId.of(UTC)),
            orderCancelledEventDomainEventPublisher
        )
    }

    override fun cancelOrder(order: Order, failureMessages: MutableList<String>) {
        TODO("Not yet implemented")
    }

    private fun validateRestaurant(restaurant: Restaurant) {
        if (restaurant.active) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException(
                "Restaurant with id " + restaurant.id!!.value
                        + " is currently not active!"
            )
        }
    }

    private fun setOrderProductInformation(order: Order, restaurant: Restaurant) {
        order.items.forEach { orderItem ->
            restaurant.products.forEach(Consumer<Product> { restaurantProduct: Product ->
                val currentProduct: Product = orderItem.product
                if (currentProduct.equals(restaurantProduct)) {
                    currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.name, restaurantProduct.price)
                }
            })
        }
    }
}
