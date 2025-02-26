package com.food.ordering.system.kotlin.order.service.dataaccess.order.mapper

import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.order.service.dataaccess.order.entity.CustomerEntity
import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import org.springframework.stereotype.Component

@Component
class CustomerDataAccessMapper {

    fun customerEntityToCustomer(customerEntity: CustomerEntity): Customer {
        return Customer(CustomerId(customerEntity.id))
    }
}