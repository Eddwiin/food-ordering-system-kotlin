package com.food.ordering.system.kotlin.order.service.domain.mapper

import com.food.ordering.system.kotlin.domain.valueobject.*
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.kotlin.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.kotlin.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Product
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.outbox.model.payment.OrderPaymentEventPayload
import com.food.ordering.system.kotlin.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderDataMapper {
    private val paymentOutboxHelper: PaymentOutboxHelper = TODO("initialize me")

    fun createOrderCommandToRestaurant(createOrderCommand: CreateOrderCommand): Restaurant {
        return Restaurant.builder()
            .restaurantId(RestaurantId(createOrderCommand.restaurantId))
            .products(createOrderCommand.items.map { orderItem -> Product(ProductId(orderItem.productId)) })
            .build()
    }

    fun createOrderCommandToOrder(createOrderCommand: CreateOrderCommand): Order {
        return Order.builder()
            .customerId(CustomerId(createOrderCommand.customerId))
            .restaurantId(RestaurantId(createOrderCommand.restaurantId))
            .streetAddress(orderAddressToStreetAddress(createOrderCommand.address))
            .price(Money(createOrderCommand.price))
            .orderItem(orderItemToOrderItemEntities(createOrderCommand.items))
            .build()
    }

    fun orderToCreateToCreateOrderResponse(order: Order, message: String): CreateOrderResponse {
        return CreateOrderResponse.builder()
            .orderTrackingId(order.trackingId!!.value!!)
            .orderStatus(order.orderStatus!!)
            .message(message)
            .build()
    }

    fun orderToTrackOrderResponse(order: Order): TrackOrderResponse {
        return TrackOrderResponse.builder()
            .orderTrackingId(order.trackingId!!.value!!)
            .orderStatus(order.orderStatus!!)
            .failureMessages(order.failureMessages)
            .build()
    }

    fun orderCreatedEventToOrderPaymentEventPayload(orderCreatedEvent: OrderCreatedEvent): OrderPaymentEventPayload {
        return OrderPaymentEventPayload(
            customerId = orderCreatedEvent.order.customerId.value.toString(),
            orderId = orderCreatedEvent.order.id?.value.toString(),
            price = orderCreatedEvent.order.price.amount,
            createdAt = orderCreatedEvent.createdAt,
            paymentOrderStatus = PaymentOrderStatus.PENDING.name
        )
    }

    private fun orderItemToOrderItemEntities(items: List<OrderItem>): MutableList<com.food.ordering.system.kotlin.order.service.domain.entity.OrderItem> {
        return items.map { item ->
            com.food.ordering.system.kotlin.order.service.domain.entity.OrderItem
                .builder()
                .product(Product(ProductId(item.productId)))
                .price(Money(item.price))
                .quantity(item.quantity)
                .subTotal(Money(item.subTotal))
                .build()
        }.toMutableList()
    }

    private fun orderAddressToStreetAddress(address: OrderAddress): com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress {
        return com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress(
            UUID.randomUUID(),
            address.street,
            address.postalCode,
            address.city
        )
    }
}