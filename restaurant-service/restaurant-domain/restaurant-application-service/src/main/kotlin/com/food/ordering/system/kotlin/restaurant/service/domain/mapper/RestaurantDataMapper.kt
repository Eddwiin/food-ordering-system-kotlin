package com.food.ordering.system.kotlin.restaurant.service.domain.mapper

import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import com.food.ordering.system.kotlin.restaurant.service.domain.dto.RestaurantApprovalRequest
import entity.OrderDetail
import entity.Product
import entity.Restaurant
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantDataMapper {
    fun restaurantApprovalRequestToRestaurant(restaurantApprovalRequest: RestaurantApprovalRequest): Restaurant {
        return Restaurant(
            restaurantId = RestaurantId(UUID.fromString(restaurantApprovalRequest.restaurantId)),
            orderDetail = OrderDetail(
                orderId = OrderId(UUID.fromString(restaurantApprovalRequest.orderId)),
                products = restaurantApprovalRequest.products?.map { product ->
                    Product(
                        productId = product.id,
                        quantity = product.quantity
                    )
                },
                totalAmount = Money(restaurantApprovalRequest.price!!),
                orderStatus = restaurantApprovalRequest.restaurantOrderStatus?.let { OrderStatus.valueOf(it.name) }
            )
        )
    }
}