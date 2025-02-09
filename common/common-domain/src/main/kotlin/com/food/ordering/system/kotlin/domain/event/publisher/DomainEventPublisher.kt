package com.food.ordering.system.kotlin.domain.event.publisher


import com.food.ordering.system.kotlin.domain.event.DomainEvent

interface DomainEventPublisher<T : DomainEvent<*>> {
    fun publish(domainEvent: T)
}