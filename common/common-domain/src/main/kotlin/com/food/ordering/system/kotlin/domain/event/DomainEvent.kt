package com.food.ordering.system.kotlin.domain.event

interface DomainEvent<T> {
    fun fire()
}