package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga

import com.food.ordering.system.kotlin.domain.event.DomainEvent

interface SagaStep<T, S : DomainEvent<*>, U : DomainEvent<*>> {
    fun process(data: T): S
    fun rollback(data: T): U
}