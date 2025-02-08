package com.food.ordering.sytem.kotlin.order.service.domain.valueobject

import com.food.ordering.system.kotlin.domain.valueobject.BaseId

data class OrderItemId(val id: Long) : BaseId<Long>(id)