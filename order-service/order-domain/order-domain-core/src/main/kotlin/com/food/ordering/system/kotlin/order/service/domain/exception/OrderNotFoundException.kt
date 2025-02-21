package com.food.ordering.system.kotlin.order.service.domain.exception

import com.food.ordering.system.kotlin.domain.exception.DomainException

class OrderNotFoundException(errorMessage: String, cause: Throwable? = null) : DomainException(errorMessage, cause)