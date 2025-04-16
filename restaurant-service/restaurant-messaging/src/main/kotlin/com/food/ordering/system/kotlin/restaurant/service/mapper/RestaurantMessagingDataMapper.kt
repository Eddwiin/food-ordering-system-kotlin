package com.food.ordering.system.kotlin.restaurant.service.mapper

import com.food.ordering.system.kotlin.domain.valueobject.ProductId
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantOrderStatus
import com.food.ordering.system.kotlin.kafka.order.avro.model.OrderApprovalStatus
import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalRequestAvroModel
import com.food.ordering.system.kotlin.kafka.order.avro.model.RestaurantApprovalResponseAvroModel
import com.food.ordering.system.kotlin.restaurant.service.domain.dto.RestaurantApprovalRequest
import entity.Product
import event.OrderApprovedEvent
import event.OrderRejectedEvent
import org.springframework.stereotype.Component
import java.util.*

@Component
class RestaurantMessagingDataMapper {

    fun orderApprovedEventToRestaurantApprovalResponseAvroModel(orderApprovedEvent: OrderApprovedEvent): RestaurantApprovalResponseAvroModel {
        return RestaurantApprovalResponseAvroModel.newBuilder()
            .setId(UUID.randomUUID().toString())
            .setSagaId("")
            .setOrderId(orderApprovedEvent.orderApproval?.orderId?.value.toString())
            .setRestaurantId(orderApprovedEvent.restaurantId?.value.toString())
            .setCreatedAt(orderApprovedEvent.createdAt?.toInstant())
            .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderApprovedEvent.orderApproval?.approvalStatus?.name!!))
            .setFailureMessages(orderApprovedEvent.failureMessages)
            .build()
    }


    fun orderRejectedEventToRestaurantApprovalResponseAvroModel(orderRejectedEvent: OrderRejectedEvent): RestaurantApprovalResponseAvroModel {
        return RestaurantApprovalResponseAvroModel.newBuilder()
            .setId(UUID.randomUUID().toString())
            .setSagaId("")
            .setOrderId(orderRejectedEvent.orderApproval?.orderId?.value.toString())
            .setRestaurantId(orderRejectedEvent.restaurantId?.value.toString())
            .setCreatedAt(orderRejectedEvent.createdAt?.toInstant())
            .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderRejectedEvent.orderApproval?.approvalStatus?.name!!))
            .setFailureMessages(orderRejectedEvent.failureMessages)
            .build()
    }


    fun restaurantApprovalRequestAvroModelToRestaurantApproval(
        restaurantApprovalRequestAvroModel: RestaurantApprovalRequestAvroModel
    ): RestaurantApprovalRequest {
        return RestaurantApprovalRequest(
            id = restaurantApprovalRequestAvroModel.id,
            sagaId = restaurantApprovalRequestAvroModel.sagaId,
            restaurantId = restaurantApprovalRequestAvroModel.restaurantId,
            orderId = restaurantApprovalRequestAvroModel.orderId,
            restaurantOrderStatus = RestaurantOrderStatus.valueOf(restaurantApprovalRequestAvroModel.restaurantOrderStatus.name),
            products = restaurantApprovalRequestAvroModel.products.map { avroModel ->
                Product(
                    productId = ProductId(UUID.fromString(avroModel.id)),
                    quantity = avroModel.quantity
                )
            },
            price = restaurantApprovalRequestAvroModel.price,
            createdAt = restaurantApprovalRequestAvroModel.createdAt
        )
    }
}