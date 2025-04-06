package event

import com.food.ordering.system.kotlin.domain.event.DomainEvent
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import entity.OrderApproval
import java.time.ZonedDateTime

abstract class OrderApprovalEvent(
    open val orderApproval: OrderApproval? = null,
    open val restaurantId: RestaurantId? = null,
    open val failureMessages: List<String>? = null,
    open val createdAt: ZonedDateTime? = null,
) : DomainEvent<OrderApproval> {
    override fun fire() {
        TODO("Not yet implemented")
    }
}