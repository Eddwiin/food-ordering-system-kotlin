package com.food.ordering.system.kotlin.domain.valueobject

abstract class BaseId<T> protected constructor(var value: T?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as BaseId<*>
        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}
