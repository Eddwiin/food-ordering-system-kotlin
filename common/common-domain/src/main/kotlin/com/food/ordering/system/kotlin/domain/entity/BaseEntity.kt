package com.food.ordering.system.kotlin.domain.entity
import java.util.Objects

abstract class BaseEntity<ID> {
    var id: ID? = null

    fun getId(): ID? {
        return id;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false
        other as BaseEntity<*>
        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }

}