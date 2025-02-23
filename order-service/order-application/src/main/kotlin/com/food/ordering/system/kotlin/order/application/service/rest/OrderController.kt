package com.food.ordering.system.kotlin.order.application.service.rest

import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.kotlin.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.ports.input.service.OrderApplicationService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(value = ["/orders"], produces = ["application/vnd.api.v1+json"])
class OrderController(
    val orderApplicationService: OrderApplicationService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun createOrder(@RequestBody createOrderCommand: CreateOrderCommand): ResponseEntity<CreateOrderResponse> {
        logger.info { "Creating order for customer: ${createOrderCommand.customerId} at restaurant: ${createOrderCommand.restaurantId}" }
        val createOrderResponse = orderApplicationService.createOrder(createOrderCommand)
        logger.info { "Order created with tracking id: ${createOrderResponse.orderTrackingId}" }
        return ResponseEntity.ok(createOrderResponse)
    }

    @GetMapping("/{trackingId}")
    fun getOrderByTrackingId(@PathVariable trackingId: UUID): ResponseEntity<TrackOrderResponse> {
        val trackOrderResponse =
            orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build())
        logger.info { "Returning order status with tracking id: ${trackOrderResponse.orderTrackingId}" }
        return ResponseEntity.ok(trackOrderResponse);
    }
}