package event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import entity.OrderApproval
import java.time.ZonedDateTime

class OrderApprovedEvent(
    override val orderApproval: OrderApproval? = null,
    override val restaurantId: RestaurantId? = null,
    override val failureMessages: MutableList<String>? = mutableListOf<String>(),
    override val createdAt: ZonedDateTime? = null,
    val orderApprovedEventDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>
) : OrderApprovalEvent(
    orderApproval,
    restaurantId,
    failureMessages,
    createdAt
) {
    override fun fire() {
        orderApprovedEventDomainEventPublisher.publish(this)
    }
}