package com.food.ordering.system.kotlin.domain.valueobject

import java.util.*

data class CustomerId(val uuid: UUID) : BaseId<UUID>(uuid)