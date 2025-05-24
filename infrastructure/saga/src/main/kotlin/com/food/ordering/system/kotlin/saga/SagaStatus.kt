package com.food.ordering.system.kotlin.saga

enum class SagaStatus {
    STARTED, FAILED, SUCCEEDED, PROCESSING, COMPENSATING, COMPENSATED
}