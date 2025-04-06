package event

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId
import entity.OrderApproval
import java.time.ZonedDateTime

class OrderRejectedEvent(
    override val orderApproval: OrderApproval? = null,
    override val restaurantId: RestaurantId? = null,
    override val failureMessages: List<String>? = mutableListOf<String>(),
    override val createdAt: ZonedDateTime? = null,
    val orderRejectedEventDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>
) : OrderApprovalEvent() {
    override fun fire() {
        orderRejectedEventDomainEventPublisher.publish(this)
    }
}