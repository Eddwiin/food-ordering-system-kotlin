package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.saga

interface SagaStep<T> {
    fun process(data: T)
    fun rollback(data: T)
}