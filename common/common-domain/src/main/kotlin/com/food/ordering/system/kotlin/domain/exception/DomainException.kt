package com.food.ordering.system.kotlin.domain.exception

open class DomainException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)