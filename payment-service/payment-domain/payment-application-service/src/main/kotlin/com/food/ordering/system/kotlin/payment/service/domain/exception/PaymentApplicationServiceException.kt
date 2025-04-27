package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.exception

import com.food.ordering.system.kotlin.domain.exception.DomainException

class PaymentApplicationServiceException(message: String, cause: Throwable? = null) : DomainException(message, cause)