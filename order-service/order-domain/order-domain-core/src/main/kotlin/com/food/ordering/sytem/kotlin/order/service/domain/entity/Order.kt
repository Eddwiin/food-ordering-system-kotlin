package com.food.ordering.sytem.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.*
import com.food.ordering.sytem.kotlin.order.service.domain.exception.OrderDomainException
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.OrderItemId
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.StreetAddress
import com.food.ordering.sytem.kotlin.order.service.domain.valueobject.TrackingId
import java.util.*

class Order(
    var customerId: CustomerId,
    var restaurantId: RestaurantId,
    var streetAddress: StreetAddress,
    var price: Money,
    var items: List<OrderItem>,
    var trackingId: TrackingId?,
    var orderStatus: OrderStatus?,
    var failureMessages: List<String>
) : AggregateRoot<OrderId>() {

    companion object {
        fun builder() = Builder();
    }

    init {
        this.id = builder().orderId
    }

    fun validateOrder() {
        validateInitialOrder()
        validateTotalPrice()
        validateItemsPrice()
    }

    private fun validateInitialOrder() {
        if (orderStatus != null && this.id != null) {
            throw OrderDomainException("Order is not in correct state for initialization");
        }
    }

    private fun validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw OrderDomainException("Total price must be greater than zero");
        }
    }

    private fun validateItemsPrice() {
        val orderItemsTotal = items.map { orderItem ->
            validateItemPrice(orderItem)
            orderItem.subTotal ?: Money.ZERO;
        }.fold(Money.ZERO, Money::add)

        if (!price.equals(orderItemsTotal)) {
            throw OrderDomainException(
                "Total price: " + price.getAmount()
                        + " is not equal to Order items total: " + orderItemsTotal.getAmount() + " !"
            );
        }
    }

    private fun validateItemPrice(orderItem: OrderItem) {
        if (!orderItem.isPriceValid()) {
            throw OrderDomainException("Order item price is not valid");
        }
    }

    fun initializeOrder() {
        this.id = OrderId(UUID.randomUUID())
        this.trackingId = TrackingId(UUID.randomUUID())
        this.orderStatus = OrderStatus.PENDING
        initializeOrderItems()
    }

    private fun initializeOrderItems() {
        var itemId: Long = 1;

        for (orderItem in items) {
            orderItem.initializeOrderItems(this.id, OrderItemId(itemId++))
        }
    }

    class Builder {
        var orderId: OrderId? = null
        var customerId: CustomerId? = null
        var restaurantId: RestaurantId? = null
        var streetAddress: StreetAddress? = null
        var price: Money? = null
        var orderItem: List<OrderItem>? = null
        var trackId: TrackingId? = null
        var orderStatus: OrderStatus? = null
        var failureMessages: List<String>? = null

        fun orderId(orderId: OrderId) = apply { this.orderId = orderId }
        fun restaurantId(restaurantId: RestaurantId) = apply { this.restaurantId = restaurantId }
        fun customerId(customerId: CustomerId) = apply { this.customerId = customerId }
        fun streetAddress(streetAddress: StreetAddress) = apply { this.streetAddress = streetAddress }
        fun price(price: Money) = apply { this.price = price }
        fun orderItem(orderItem: List<OrderItem>) = apply { this.orderItem = orderItem }
        fun trackId(trackId: TrackingId) = apply { this.trackId = trackId }
        fun orderStatus(orderStatus: OrderStatus) = apply { this.orderStatus = orderStatus }
        fun failureMessages(failureMessages: List<String>) = apply { this.failureMessages = failureMessages }

        fun build() = Order(
            customerId = customerId!!,
            restaurantId = restaurantId!!,
            streetAddress = streetAddress!!,
            price = price!!,
            items = orderItem!!,
            trackingId = trackId,
            orderStatus = orderStatus,
            failureMessages = failureMessages ?: emptyList()
        )
    }
}