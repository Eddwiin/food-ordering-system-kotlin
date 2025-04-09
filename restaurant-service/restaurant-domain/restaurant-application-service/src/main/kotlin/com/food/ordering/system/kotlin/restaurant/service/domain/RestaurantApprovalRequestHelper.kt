package com.food.ordering.system.kotlin.restaurant.service.domain

import RestaurantDomainService
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.restaurant.service.domain.dto.RestaurantApprovalRequest
import com.food.ordering.system.kotlin.restaurant.service.domain.mapper.RestaurantDataMapper
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository.OrderApprovalRepository
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository.RestaurantRepository
import entity.Restaurant
import event.OrderApprovalEvent
import exception.RestaurantNotFoundException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Component
open class RestaurantApprovalRequestHelper(
    val restaurantDomainService: RestaurantDomainService? = null,
    val restaurantDataMapper: RestaurantDataMapper? = null,
    val restaurantRepository: RestaurantRepository? = null,
    val orderApprovalRepository: OrderApprovalRepository? = null,
    val orderApprovedMessagePublisher: OrderApprovedMessagePublisher? = null,
    val orderRejectedMessagePublisher: OrderRejectedMessagePublisher? = null,
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun persistOrderApproval(restaurantApprovalRequest: RestaurantApprovalRequest): OrderApprovalEvent {
        logger.info { "Processing restaurant approval for order id: ${restaurantApprovalRequest.orderId}" }
        val failureMessages = mutableListOf<String>()

        val restaurant = findRestaurant(restaurantApprovalRequest)
        val orderApprovalEvent =
            restaurantDomainService!!.validateOrder(
                restaurant,
                failureMessages,
                orderApprovedMessagePublisher,
                orderRejectedMessagePublisher
            )
        orderApprovalRepository!!.save(restaurant.orderApproval!!)
        return orderApprovalEvent!!
    }

    private fun findRestaurant(restaurantApprovalRequest: RestaurantApprovalRequest): Restaurant {

        val restaurant = restaurantDataMapper?.restaurantApprovalRequestToRestaurant(restaurantApprovalRequest)
            ?: throw IllegalArgumentException("Failed to map restaurantApprovalRequest to Restaurant")

        val restaurantResult = restaurantRepository?.findRestaurantInformation(restaurant)
            ?: throw IllegalStateException("RestaurantRepository not initialized")

        if (restaurantResult == null) {
            logger.error { "Restaurant with id ${restaurant.restaurantId.uuid} not found!" }
            throw RestaurantNotFoundException("Restaurant with id ${restaurant.restaurantId.uuid} not found!")
        }

        restaurant.active = restaurantResult.active
        restaurant.orderDetail?.products?.forEach { product ->
            restaurantResult.orderDetail?.products?.forEach { p ->
                if (p.id == product.id) {
                    product.name = p.name
                    product.price = p.price
                    product.available = p.available
                }
            }
        }
        restaurant.orderDetail?.orderId = OrderId(UUID.fromString(restaurantApprovalRequest.orderId))

        return restaurant
    }
}