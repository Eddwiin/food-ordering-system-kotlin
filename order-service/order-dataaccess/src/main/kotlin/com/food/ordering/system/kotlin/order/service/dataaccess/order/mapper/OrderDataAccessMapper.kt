package com.food.ordering.system.kotlin.order.service.dataaccess.order.mapper

import com.food.ordering.system.kotlin.domain.valueobject.*
import com.food.ordering.system.kotlin.order.service.dataaccess.order.entity.OrderAddressEntity
import com.food.ordering.system.kotlin.order.service.dataaccess.order.entity.OrderEntity
import com.food.ordering.system.kotlin.order.service.dataaccess.order.entity.OrderItemEntity
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.OrderItem
import com.food.ordering.system.kotlin.order.service.domain.entity.Product
import com.food.ordering.system.kotlin.order.service.domain.valueobject.OrderItemId
import com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress
import org.springframework.stereotype.Component

@Component
class OrderDataAccessMapper {

    fun orderToOrderEntity(order: Order): OrderEntity {
        val orderEntity = OrderEntity.builder()
            .id(order.id!!.value)
            .customerId(order.customerId.value)
            .restaurantId(order.restaurantId.value)
            .trackingId(order.trackingId!!.value)
            .address(deliveryAddressToAddressEntity(order.streetAddress))
            .price(order.price.amount)
            .items(orderItemsToOrderItemEntity(order.items))
            .orderStatus(order.orderStatus)
            .failureMessages(order.failureMessages?.joinToString(Order.FAILURE_MESSAGE_DELIMITER))
            .build()

        orderEntity.address?.order = orderEntity
        orderEntity.items.forEach { orderItemEntity ->
            orderItemEntity.order = orderEntity
        }

        return orderEntity
    }

    fun orderEntityToOrder(orderEntity: OrderEntity): Order {
        return Order.builder()
            .orderId(OrderId(orderEntity.id!!))
            .customerId(CustomerId(orderEntity.customerId!!))
            .restaurantId(RestaurantId(orderEntity.restaurantId!!))
            .streetAddress(addressEntityToDeliveryAddress(orderEntity.address!!))
            .price(Money(orderEntity.price!!))
            .orderItem(orderItemEntitiesToOrderItems(orderEntity.items))
            .trackId(com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId(orderEntity.trackingId!!))
            .orderStatus(orderEntity.orderStatus!!)
            .failureMessages(
                if (orderEntity.failureMessages.isNullOrEmpty()) mutableListOf()
                else orderEntity.failureMessages.split(Order.FAILURE_MESSAGE_DELIMITER).toMutableList()
            )
            .build()
    }

    private fun addressEntityToDeliveryAddress(addressEntity: OrderAddressEntity): StreetAddress {
        return StreetAddress(
            id = addressEntity.id!!,
            street = addressEntity.street!!,
            city = addressEntity.city!!,
            postalCode = addressEntity.postalCode!!
        )
    }

    private fun orderItemEntitiesToOrderItems(items: List<OrderItemEntity>): List<OrderItem> {
        return items.map { orderItemEntity ->
            OrderItem.builder()
                .orderItemId(OrderItemId(orderItemEntity.id))
                .product(Product(ProductId(orderItemEntity.productId!!)))
                .price(Money(orderItemEntity.price!!))
                .quantity(orderItemEntity.quantity!!)
                .subTotal(Money(orderItemEntity.subTotal!!))
                .build()
        }
    }

    private fun orderItemsToOrderItemEntity(items: List<OrderItem>): List<OrderItemEntity> {
        return items.map { orderItem ->
            OrderItemEntity.builder()
                .id(orderItem.id!!.value!!)
                .productId(orderItem.product.productId.value)
                .price(orderItem.product.price!!.amount)
                .quantity(orderItem.quantity)
                .subTotal(orderItem.subTotal!!.amount)
                .build()
        }
    }

    private fun deliveryAddressToAddressEntity(streetAddress: StreetAddress): OrderAddressEntity? {
        return OrderAddressEntity.builder()
            .id(streetAddress.id)
            .street(streetAddress.street)
            .city(streetAddress.city)
            .postalCode(streetAddress.postalCode)
            .build()
    }
}