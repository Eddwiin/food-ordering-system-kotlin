package com.food.ordering.system.kotlin.order.service.messaging.listener.kafka

import com.food.ordering.system.kafka.consumer.KafkaConsumer
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import com.food.ordering.system.kotlin.kafka.order.avro.model.PaymentResponseAvroModel
import com.food.ordering.system.kotlin.order.service.domain.ports.input.message.listener.payment.PaymentResponseListener
import com.food.ordering.system.kotlin.order.service.messaging.mapper.OrderMessagingDataMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class PaymentResponseKafkaListener(
    val paymentResponseMessageListener: PaymentResponseListener,
    val orderMessagingDataMapper: OrderMessagingDataMapper
) : KafkaConsumer<PaymentResponseAvroModel> {

    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        id = "\${kafka.consumer.config.payment.consumer.group.id}",
        topics = ["order-service.payment-response-topic-name"]
    )
    override fun receive(
        @Payload messages: List<PaymentResponseAvroModel>,
        @Header(KafkaHeaders.RECEIVED_KEY) keys: List<String>,
        @Header(KafkaHeaders.PARTITION) partitions: List<Int>,
        @Header(KafkaHeaders.OFFSET) offsets: List<Long>
    ) {
        logger.info { "${messages.size} number of payment reponses received with keys: ${keys.toString()}, partitions: ${partitions.toString()}, offsets: ${offsets.toString()}" }

        messages.forEach { paymentResponseAvroModel ->
            if (paymentResponseAvroModel.paymentStatus == mapToPaymentStatus(PaymentStatus.COMPLETED)) {
                logger.info { "Processing successful payment for order id: ${paymentResponseAvroModel.orderId}" }
                paymentResponseMessageListener.paymentCompleted(
                    orderMessagingDataMapper.paymentRequestAvroModelToPaymentOrderStatus(
                        paymentResponseAvroModel
                    )
                )
            } else if (paymentResponseAvroModel.paymentStatus == mapToPaymentStatus(PaymentStatus.CANCELLED) ||
                paymentResponseAvroModel.paymentStatus == mapToPaymentStatus(PaymentStatus.FAILED)
            ) {
                logger.info { "Processing unsuccessful payment for order id: ${paymentResponseAvroModel.orderId}" }
                paymentResponseMessageListener.paymentCancelled(
                    orderMessagingDataMapper.paymentRequestAvroModelToPaymentOrderStatus(
                        paymentResponseAvroModel
                    )
                )
            }

        }
    }

    private fun mapToPaymentStatus(paymentOrderStatus: PaymentStatus): PaymentStatus? {
        return when (paymentOrderStatus) {
            PaymentStatus.COMPLETED -> PaymentStatus.COMPLETED
            PaymentStatus.CANCELLED -> PaymentStatus.CANCELLED
            PaymentStatus.FAILED -> PaymentStatus.FAILED
            else -> null
        }
    }
}