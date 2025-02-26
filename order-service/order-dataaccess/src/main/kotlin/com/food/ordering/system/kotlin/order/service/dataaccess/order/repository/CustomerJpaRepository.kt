package com.food.ordering.system.kotlin.order.service.dataaccess.order.repository

import com.food.ordering.system.kotlin.order.service.dataaccess.order.entity.CustomerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerJpaRepository : JpaRepository<CustomerEntity, UUID> {
}