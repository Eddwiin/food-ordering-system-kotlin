package com.food.ordering.system.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId

class Customer(customerId: CustomerId) : AggregateRoot<CustomerId>()