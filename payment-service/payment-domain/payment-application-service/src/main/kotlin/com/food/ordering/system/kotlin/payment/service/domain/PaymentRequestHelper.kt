package com.food.ordering.system.kotlin.payment.service.domain

import PaymentDomainService
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.dto.PaymentRequest
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.exception.PaymentApplicationServiceException
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.service.domain.mapper.PaymentDataMapper
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.message.publisher.PaymentFailedMessagePublisher
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.CreditEntryRepository
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.CreditHistoryRepository
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.PaymentRepository
import entity.CreditEntry
import entity.CreditHistory
import entity.Payment
import event.PaymentEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
open class PaymentRequestHelper(
    val paymentDomainService: PaymentDomainService,
    val paymentDataMapper: PaymentDataMapper,
    val paymentRepository: PaymentRepository,
    val creditHistoryRepository: CreditHistoryRepository,
    val creditEntryRepository: CreditEntryRepository,
    val paymentCompletedEventDomainEventPublisher: PaymentCompletedMessagePublisher? = null,
    val paymentCancelledEventDomainEventPublisher: PaymentCancelledMessagePublisher? = null,
    val paymentFailedEventDomainEventPublisher: PaymentFailedMessagePublisher? = null
) {
    private val logger = KotlinLogging.logger {}

    @Transactional
    open fun persistPayment(paymentRequest: PaymentRequest): PaymentEvent {
        logger.info { "Received payment complete event for order id: ${paymentRequest.orderId}" }
        val payment: Payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest)
        val creditEntry: CreditEntry = getCreditEntry(payment.customerId)
        val creditHistories: MutableList<CreditHistory> = getCreditHistory(payment.customerId)
        val failureMessages: MutableList<String> = ArrayList<String>()
        val paymentEvent =
            paymentDomainService.validateAndInitiatePayment(
                payment, creditEntry, creditHistories, failureMessages,
                paymentCompletedEventDomainEventPublisher, paymentFailedEventDomainEventPublisher
            )
        persistDbObjects(payment, creditEntry, creditHistories, failureMessages)
        return paymentEvent
    }


    @Transactional
    open fun persistCancelPayment(paymentRequest: PaymentRequest): PaymentEvent {
        logger.info { "Received payment rollback event for order id: ${paymentRequest.orderId}" }
        val paymentResponse = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.orderId))
            ?: run {
                logger.error { "Payment with order id: ${paymentRequest.orderId} could not be found!" }
                throw PaymentApplicationServiceException("Payment with order id: ${paymentRequest.orderId} could not be found!")
            }
        val creditEntry = getCreditEntry(paymentResponse.customerId!!)
        val creditHistories: MutableList<CreditHistory> = getCreditHistory(paymentResponse.customerId)
        val failureMessages: MutableList<String> = mutableListOf()
        val paymentEvent = paymentDomainService.validateAndCancelPayment(
            paymentResponse, creditEntry, creditHistories, failureMessages,
            paymentCancelledEventDomainEventPublisher!!, paymentFailedEventDomainEventPublisher!!
        )
        persistDbObjects(paymentResponse, creditEntry, creditHistories, failureMessages)
        return paymentEvent
    }

    private fun getCreditEntry(customerId: CustomerId): CreditEntry {
        val creditEntry: CreditEntry? = creditEntryRepository.findByCustomerId(customerId)
        if (creditEntry == null) {
            logger.error { "Could not find credit entry for customer: ${customerId.value}" }
            throw PaymentApplicationServiceException(
                "Could not find credit entry for customer: " +
                        customerId.value
            )
        }
        return creditEntry
    }

    private fun getCreditHistory(customerId: CustomerId): MutableList<CreditHistory> {
        val creditHistories: MutableList<CreditHistory>? =
            creditHistoryRepository.findByCustomerId(customerId)
        if (creditHistories.isNullOrEmpty()) {
            logger.error { "Could not find credit history for customer: ${customerId.value}" }
            throw PaymentApplicationServiceException(
                "Could not find credit history for customer: " +
                        customerId.value
            )
        }
        return creditHistories
    }

    private fun persistDbObjects(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>?,
        failureMessages: MutableList<String>?
    ) {
        paymentRepository.save(payment)
        if (failureMessages.isNullOrEmpty()) {
            creditEntryRepository.save(creditEntry)
            creditHistoryRepository.save(creditHistories?.get(creditHistories.size - 1)!!)
        }
    }

}