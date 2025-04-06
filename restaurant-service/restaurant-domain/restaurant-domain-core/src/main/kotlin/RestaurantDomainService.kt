import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import entity.Restaurant
import event.OrderApprovalEvent
import event.OrderApprovedEvent
import event.OrderRejectedEvent

interface RestaurantDomainService {
    fun validateOrder(
        restaurant: Restaurant?,
        failureMessages: MutableList<String>?,
        orderApprovedEventDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>?,
        orderRejectedEventDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>?
    ): OrderApprovalEvent?
}