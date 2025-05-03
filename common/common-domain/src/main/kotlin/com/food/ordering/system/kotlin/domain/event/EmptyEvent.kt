package com.food.ordering.system.kotlin.domain.event

class EmptyEvent : DomainEvent<Void> {
    companion object {
        val instance = EmptyEvent()
    }

    override fun fire() {
        TODO("Not yet implemented")
    }
}