package com.food.ordering.system.kotlin.restaurant.service.dataaccess.entity

import com.food.ordering.system.kotlin.domain.valueobject.OrderApprovalStatus
import jakarta.persistence.*
import java.util.*

@Table(name = "order_approval", schema = "restaurant")
@Entity
class OrderApprovalEntity(
    @Id
    val id: UUID? = null,
    val restaurantId: UUID? = null,
    val orderId: UUID? = null,
    @Enumerated(EnumType.STRING)
    val status: OrderApprovalStatus? = null,
)