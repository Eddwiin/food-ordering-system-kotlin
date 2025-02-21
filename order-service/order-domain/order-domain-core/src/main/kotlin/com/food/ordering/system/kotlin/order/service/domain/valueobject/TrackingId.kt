package com.food.ordering.system.kotlin.order.service.domain.valueobject

import com.food.ordering.system.kotlin.domain.valueobject.BaseId
import java.util.*

data class TrackingId(val uuid: UUID) : BaseId<UUID>(uuid)
