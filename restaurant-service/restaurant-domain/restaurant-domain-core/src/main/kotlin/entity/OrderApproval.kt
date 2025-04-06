package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.OrderApprovalStatus
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import valueobject.OrderApprovalId

class OrderApproval(
    var orderApprovalId: OrderApprovalId? = null,
    var restaurantId: RestaurantId? = null,
    var orderId: OrderId? = null,
    var approvalStatus: OrderApprovalStatus? = null
) : BaseEntity<OrderApprovalId>()