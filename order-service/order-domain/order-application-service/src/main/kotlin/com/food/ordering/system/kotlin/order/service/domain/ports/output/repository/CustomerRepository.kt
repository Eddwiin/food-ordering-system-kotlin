package com.food.ordering.system.kotlin.order.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository {
    fun findCustomer(uuid: UUID): Optional<Customer>
}