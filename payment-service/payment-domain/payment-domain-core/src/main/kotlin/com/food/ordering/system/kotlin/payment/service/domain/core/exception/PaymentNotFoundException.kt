package com.food.ordering.system.kotlin.payment.service.domain.core.exception

import com.food.ordering.system.kotlin.domain.exception.DomainException

class PaymentNotFoundException(message: String, cause: Throwable?) : DomainException(message, cause)