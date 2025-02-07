package com.food.ordering.system.kotlin.domain.valueobject

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import java.util.*

abstract class BaseId<T> protected constructor(var value: T?) {

    fun getValue(): T? {
        return value
    }

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
