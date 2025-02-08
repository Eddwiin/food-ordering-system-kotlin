package com.food.ordering.system.kotlin.domain.valueobject

import java.util.*

data class OrderId(val uuid: UUID) : BaseId<UUID>(uuid)