package com.food.ordering.system.kotlin.outbox

enum class OutboxStatus {
    STARTED, COMPLETED, FAILED
}