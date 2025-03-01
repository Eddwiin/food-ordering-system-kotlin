package com.food.ordering.system.kotlin.order.service.dataaccess.order.adapter

import com.food.ordering.system.kotlin.order.service.dataaccess.order.mapper.CustomerDataAccessMapper
import com.food.ordering.system.kotlin.order.service.dataaccess.order.repository.CustomerJpaRepository
import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.CustomerRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
open class CustomerRepositoryImpl(
    val customerJpaRepository: CustomerJpaRepository,
    val customerDataAccessMapper: CustomerDataAccessMapper
) : CustomerRepository {

    override fun findCustomer(uuid: UUID): Optional<Customer> {
        return customerJpaRepository.findById(uuid).map { customerDataAccessMapper.customerEntityToCustomer(it) }
    }
}