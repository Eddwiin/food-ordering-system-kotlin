package com.food.ordering.system.kotlin.order.service.dataaccess.order.adapter

import com.food.ordering.system.kotlin.order.service.dataaccess.order.mapper.OrderDataAccessMapper
import com.food.ordering.system.kotlin.order.service.dataaccess.order.repository.OrderJpaRepository
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.order.service.domain.valueobject.TrackingId
import org.springframework.stereotype.Component

@Component
class OrderRepositoryImpl(
    val orderJpaRepository: OrderJpaRepository,
    val orderDataAccessMapper: OrderDataAccessMapper
) : OrderRepository {

    override fun save(order: Order): Order {
        return orderDataAccessMapper.orderEntityToOrder(
            orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order))
        )
    }

    override fun findByTrackingId(trackingId: TrackingId): Order {
        return orderDataAccessMapper.orderEntityToOrder(
            orderJpaRepository.findByTrackingId(trackingId.value!!)!!
        )
    }
}