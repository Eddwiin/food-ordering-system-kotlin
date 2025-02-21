package com.food.ordering.system.kotlin.order.service.domain.valueobject

import com.food.ordering.system.kotlin.domain.valueobject.BaseId

data class OrderItemId(val id: Long) : BaseId<Long>(id)