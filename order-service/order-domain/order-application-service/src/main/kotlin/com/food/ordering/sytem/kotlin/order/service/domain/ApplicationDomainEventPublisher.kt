package com.food.ordering.sytem.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.sytem.kotlin.order.service.domain.event.OrderCreatedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component

@Component
class ApplicationDomainEventPublisher(
    var applicationEventPublisher: ApplicationEventPublisher
) : ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {
    private val logger = KotlinLogging.logger {}

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher
    }

    override fun publish(domainEvent: OrderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(domainEvent)
        logger.info { "OrderCreatedEvent is published for order id: ${domainEvent.order.id!!.value}" }
    }

}