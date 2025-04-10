package com.food.ordering.system.kotlin.restaurant.service.dataaccess.adapter

import com.food.ordering.system.kotlin.restaurant.service.dataaccess.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.kotlin.restaurant.service.dataaccess.repository.OrderApprovalJpaRepository
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository.OrderApprovalRepository
import entity.OrderApproval
import org.springframework.stereotype.Component

@Component
class OrderApprovalRepositoryImpl(
    val orderApprovalJpaRepository: OrderApprovalJpaRepository? = null,
    val restaurantDataAccessMapper: RestaurantDataAccessMapper? = null
) : OrderApprovalRepository {
    override fun save(orderApproval: OrderApproval): OrderApproval {
        TODO("Not yet implemented")
    }
}