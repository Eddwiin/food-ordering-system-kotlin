package com.food.ordering.system.kotlin.restaurant.service.dataaccess.adapter

import com.food.ordering.system.kotlin.restaurant.service.dataaccess.mapper.RestaurantDataAccessMapper
import com.food.ordering.system.kotlin.restaurant.service.dataaccess.repository.OrderApprovalJpaRepository
import com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository.OrderApprovalRepository
import entity.OrderApproval
import org.springframework.stereotype.Component

@Component
class OrderApprovalRepositoryImpl(
    val orderApprovalJpaRepository: OrderApprovalJpaRepository,
    val restaurantDataAccessMapper: RestaurantDataAccessMapper,
) : OrderApprovalRepository {
    override fun save(orderApproval: OrderApproval): OrderApproval {
        return restaurantDataAccessMapper.orderApprovalEntityToOrderApproval(
            orderApprovalJpaRepository.save(restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval))
        )
    }
}