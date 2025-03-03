package com.food.ordering.system.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.*
import com.food.ordering.system.kotlin.order.service.domain.valueobject.OrderItemId
import com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress
import com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId
import java.util.*

class Order private constructor(
    var customerId: CustomerId,
    var restaurantId: RestaurantId,
    var streetAddress: StreetAddress,
    var price: Money,
    var items: MutableList<OrderItem>,
    var trackingId: TrackingId?,
    var orderStatus: OrderStatus?,
    var failureMessages: MutableList<String>
) : AggregateRoot<OrderId>() {

    companion object {
        fun builder() = Builder();
        val FAILURE_MESSAGE_DELIMITER = ","
    }

    init {
        this.id = builder().orderId
    }

    fun validateOrder() {
        validateInitialOrder()
        validateTotalPrice()
        validateItemsPrice()
    }

    fun pay() {
        if (orderStatus != OrderStatus.PENDING) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order is not in correct state for pay operation!")
        }

        orderStatus = OrderStatus.PAID
    }

    fun approve() {
        if (orderStatus != OrderStatus.PAID) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order is not in correct state for approve operation!")
        }

        orderStatus = OrderStatus.APPROVED
    }

    fun initCancel(failureMessages: MutableList<String>) {
        if (orderStatus != OrderStatus.PAID) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order is not in correct state for cancel operation!")
        }

        orderStatus = OrderStatus.CANCELLING
        updateFailureMessages(failureMessages)
    }

    fun cancel(failureMessages: MutableList<String>) {
        if (orderStatus != OrderStatus.PAID || orderStatus != OrderStatus.CANCELLING) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order is not in correct state for cancel operation!")
        }

        orderStatus = OrderStatus.CANCELLED
        updateFailureMessages(failureMessages)
    }

    private fun validateInitialOrder() {
        if (orderStatus != null && this.id != null) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order is not in correct state for initialization");
        }
    }

    private fun validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Total price must be greater than zero");
        }
    }

    private fun validateItemsPrice() {
        val orderItemsTotal = items.map { orderItem ->
            validateItemPrice(orderItem)
            orderItem.subTotal ?: Money.ZERO;
        }.fold(Money.ZERO, Money::add)

        if (!price.equals(orderItemsTotal)) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException(
                "Total price: " + price.amount
                        + " is not equal to Order items total: " + orderItemsTotal.amount + " !"
            );
        }
    }

    private fun validateItemPrice(orderItem: OrderItem) {
        if (!orderItem.isPriceValid()) {
            throw com.food.ordering.system.kotlin.order.service.domain.exception.OrderDomainException("Order item price is not valid");
        }
    }

    fun initializeOrder() {
        this.id = OrderId(UUID.randomUUID())
        this.trackingId = com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId(UUID.randomUUID())
        this.orderStatus = OrderStatus.PENDING
        initializeOrderItems()
    }

    private fun initializeOrderItems() {
        var itemId: Long = 1;

        for (orderItem in items) {
            orderItem.initializeOrderItems(this.id, OrderItemId(itemId++))
        }
    }

    private fun updateFailureMessages(failureMessages: MutableList<String>) {
        if (this.failureMessages != null && failureMessages != null) {
            failureMessages.filter { failureMessage -> !failureMessage.isEmpty() }.let {
                this.failureMessages.addAll(it)
            }
        }

        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    class Builder {
        var orderId: OrderId? = null
        var customerId: CustomerId? = null
        var restaurantId: RestaurantId? = null
        var streetAddress: com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress? = null
        var price: Money? = null
        var orderItem: MutableList<OrderItem>? = null
        var trackId: com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId? = null
        var orderStatus: OrderStatus? = null
        var failureMessages: MutableList<String>? = null

        fun orderId(orderId: OrderId) = apply { this.orderId = orderId }
        fun restaurantId(restaurantId: RestaurantId) = apply { this.restaurantId = restaurantId }
        fun customerId(customerId: CustomerId) = apply { this.customerId = customerId }
        fun streetAddress(streetAddress: com.food.ordering.system.kotlin.order.service.domain.valueobject.StreetAddress) =
            apply { this.streetAddress = streetAddress }

        fun price(price: Money) = apply { this.price = price }
        fun orderItem(orderItem: MutableList<OrderItem>) = apply { this.orderItem = orderItem }
        fun trackId(trackId: com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId) =
            apply { this.trackId = trackId }

        fun orderStatus(orderStatus: OrderStatus) = apply { this.orderStatus = orderStatus }
        fun failureMessages(failureMessages: MutableList<String>) = apply { this.failureMessages = failureMessages }

        fun build() = Order(
            customerId = customerId!!,
            restaurantId = restaurantId!!,
            streetAddress = streetAddress!!,
            price = price!!,
            items = orderItem!!,
            trackingId = trackId,
            orderStatus = orderStatus,
            failureMessages = failureMessages ?: mutableListOf()
        )
    }
}