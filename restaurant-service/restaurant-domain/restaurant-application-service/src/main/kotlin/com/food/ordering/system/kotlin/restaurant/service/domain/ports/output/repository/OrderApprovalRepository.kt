package com.food.ordering.system.kotlin.restaurant.service.domain.ports.output.repository

import entity.OrderApproval

interface OrderApprovalRepository {
    fun save(orderApproval: OrderApproval): OrderApproval
}