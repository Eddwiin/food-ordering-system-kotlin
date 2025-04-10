package com.food.ordering.system.kotlin.restaurant.service.dataaccess.repository

import com.food.ordering.system.kotlin.restaurant.service.dataaccess.entity.OrderApprovalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderApprovalJpaRepository : JpaRepository<OrderApprovalEntity, UUID>