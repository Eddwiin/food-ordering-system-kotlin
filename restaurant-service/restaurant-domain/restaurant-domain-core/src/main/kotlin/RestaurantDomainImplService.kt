import com.food.ordering.system.kotlin.domain.DomainConstants.Companion.UTC
import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.domain.valueobject.OrderApprovalStatus
import entity.Restaurant
import event.OrderApprovalEvent
import event.OrderApprovedEvent
import event.OrderRejectedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import valueobject.OrderApprovalId
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

class RestaurantDomainImplService : RestaurantDomainService {
    private val logger = KotlinLogging.logger {}

    override fun validateOrder(
        restaurant: Restaurant,
        failureMessages: MutableList<String>?,
        orderApprovedEventDomainEventPublisher: DomainEventPublisher<OrderApprovedEvent>?,
        orderRejectedEventDomainEventPublisher: DomainEventPublisher<OrderRejectedEvent>?
    ): OrderApprovalEvent {
        restaurant.validateOrder(failureMessages)
        logger.info { "Validating order with id: ${restaurant.orderDetail?.id?.value}" }

        if (failureMessages!!.isEmpty()) {
            logger.info { "Order is approved for order id: ${restaurant.orderDetail?.id?.value}" }

            restaurant.orderApproval?.orderApprovalId = OrderApprovalId(UUID.randomUUID())
            restaurant.orderApproval?.restaurantId = restaurant.id
            restaurant.orderApproval?.orderId = restaurant.orderDetail?.id
            restaurant.orderApproval?.approvalStatus = OrderApprovalStatus.APPROVED

            return OrderApprovedEvent(
                restaurant.orderApproval,
                restaurant.id,
                failureMessages,
                ZonedDateTime.now(ZoneId.of(UTC)),
                orderApprovedEventDomainEventPublisher!!
            )
        } else {
            logger.info { "Order is rejected for order id: ${restaurant.orderDetail?.id?.value}" }

            restaurant.orderApproval?.orderApprovalId = OrderApprovalId(UUID.randomUUID())
            restaurant.orderApproval?.restaurantId = restaurant.id
            restaurant.orderApproval?.orderId = restaurant.orderDetail?.id
            restaurant.orderApproval?.approvalStatus = OrderApprovalStatus.REJECTED

            return OrderRejectedEvent(
                restaurant.orderApproval,
                restaurant.id,
                failureMessages,
                ZonedDateTime.now(ZoneId.of(UTC)),
                orderRejectedEventDomainEventPublisher!!
            )
        }
    }
}