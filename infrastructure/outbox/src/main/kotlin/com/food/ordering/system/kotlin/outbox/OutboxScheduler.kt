package com.food.ordering.system.kotlin.outbox

interface OutboxScheduler {
    fun processOutboxMessage()
}