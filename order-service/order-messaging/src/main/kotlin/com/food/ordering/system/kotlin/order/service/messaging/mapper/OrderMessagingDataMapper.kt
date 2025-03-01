package com.food.ordering.system.kotlin.order.service.messaging.mapper

import com.food.ordering.system.kotlin.domain.valueobject.OrderApprovalStatus
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import com.food.ordering.system.kotlin.kafka.order.avro.model.*
import com.food.ordering.system.kotlin.order.service.domain.dto.message.PaymentResponse
import com.food.ordering.system.kotlin.order.service.domain.dto.message.RestaurantApprovalResponse
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCancelledEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderCreatedEvent
import com.food.ordering.system.kotlin.order.service.domain.event.OrderPaidEvent
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrderMessagingDataMapper {

    fun orderCreatedEventToPaymentRequestAvroModel(orderCreatedEvent: OrderCreatedEvent): PaymentRequestAvroModel {
        val order = orderCreatedEvent.order

        return PaymentRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID().toString())
            .setSagaId("")
            .setCustomerId(order.customerId.value.toString())
            .setOrderId(order.id!!.value.toString())
            .setPrice(order.price.amount)
            .setCreatedAt(orderCreatedEvent.createdAt.toInstant())
            .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
            .build()
    }


    fun orderCancelledEventToPaymentRequestAvroModel(orderCancelledEvent: OrderCancelledEvent): PaymentRequestAvroModel {
        val order = orderCancelledEvent.order

        return PaymentRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID().toString())
            .setSagaId("")
            .setCustomerId(order.customerId.value.toString())
            .setOrderId(order.id!!.value.toString())
            .setPrice(order.price.amount)
            .setCreatedAt(orderCancelledEvent.createdAt.toInstant())
            .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
            .build()
    }

    fun orderPaidEventToRestaurantApprovalRequestAvroModel(orderPaidEvent: OrderPaidEvent): RestaurantApprovalRequestAvroModel {

        val order = orderPaidEvent.order
        return RestaurantApprovalRequestAvroModel.newBuilder()
            .setId(UUID.randomUUID().toString())
            .setSagaId("")
            .setOrderId(order.id!!.value.toString())
            .setRestaurantId(order.restaurantId!!.value.toString())
            .setOrderId(order.id!!.value.toString())
            .setRestaurantOrderStatus(
                RestaurantOrderStatus
                    .valueOf(order.orderStatus!!.name)
            )
            .setProducts(order.items.map { orderItem ->
                com.food.ordering.system.kotlin.kafka.order.avro.model.Product.newBuilder()
                    .setId(orderItem.product.id!!.value.toString())
                    .setQuantity(orderItem.quantity)
                    .build()
            })
            .setPrice(order.price.amount)
            .setCreatedAt(orderPaidEvent.createdAt.toInstant())
            .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
            .build()
    }

    fun paymentRequestAvroModelToPaymentOrderStatus(paymentResponseAvroModel: PaymentResponseAvroModel): PaymentResponse {
        return PaymentResponse.builder()
            .id(paymentResponseAvroModel.getId())
            .sagaId(paymentResponseAvroModel.getSagaId())
            .paymentId(paymentResponseAvroModel.getPaymentId())
            .customerId(paymentResponseAvroModel.getCustomerId())
            .orderId(paymentResponseAvroModel.getOrderId())
            .price(paymentResponseAvroModel.getPrice())
            .createdAt(paymentResponseAvroModel.getCreatedAt())
            .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name))
            .failureMessages(paymentResponseAvroModel.getFailureMessages())
            .build();
    }


    fun approvalResponseAvroModelToApprovalResponse(
        restaurantApprovalResponseAvroModel: RestaurantApprovalResponseAvroModel
    ): RestaurantApprovalResponse {
        return RestaurantApprovalResponse.builder()
            .id(restaurantApprovalResponseAvroModel.id)
            .sagaId(restaurantApprovalResponseAvroModel.sagaId)
            .restaurantId(restaurantApprovalResponseAvroModel.restaurantId)
            .orderId(restaurantApprovalResponseAvroModel.orderId)
            .createdAt(restaurantApprovalResponseAvroModel.createdAt)
            .orderApprovalStatus(
                OrderApprovalStatus.valueOf(
                    restaurantApprovalResponseAvroModel.orderApprovalStatus.name
                )
            )
            .failuresMessage(restaurantApprovalResponseAvroModel.failureMessages)
            .build()
    }
}