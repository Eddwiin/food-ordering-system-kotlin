package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import java.util.*

interface CustomerRepository {
    fun findCustomer(uuid: UUID): Optional<Customer>
}