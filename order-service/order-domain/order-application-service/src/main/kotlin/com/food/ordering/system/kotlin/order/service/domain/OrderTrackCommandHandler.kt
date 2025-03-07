package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.order.service.domain.dto.track.TrackOrderQuery
import com.food.ordering.system.kotlin.order.service.domain.dto.track.TrackOrderResponse
import com.food.ordering.system.kotlin.order.service.domain.exception.OrderNotFoundException
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
open class OrderTrackCommandHandler(
    val orderRepository: OrderRepository,
    val orderDataMapper: OrderDataMapper
) {
    private val logger = KotlinLogging.logger {}

    @Transactional(readOnly = true)
    open fun trackOrder(trackOrderQuery: TrackOrderQuery): TrackOrderResponse {
        val orderResult =
            orderRepository.findByTrackingId(
                TrackingId(
                    trackOrderQuery.orderTrackingId
                )
            )

        if (orderResult == null) {
            logger.info { "Could no find order with tracking id ${trackOrderQuery.orderTrackingId}" }
            throw OrderNotFoundException("Could no find order with tracking id ${trackOrderQuery.orderTrackingId}")
        }

        return orderDataMapper.orderToTrackOrderResponse(orderResult)
    }
}