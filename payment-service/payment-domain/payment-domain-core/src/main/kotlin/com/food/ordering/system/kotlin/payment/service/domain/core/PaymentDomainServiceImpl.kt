package com.food.ordering.system.kotlin.payment.service.domain.core

import com.food.ordering.system.kotlin.domain.DomainConstants
import com.food.ordering.system.kotlin.domain.event.publisher.DomainEventPublisher
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.PaymentStatus
import entity.CreditEntry
import entity.CreditHistory
import entity.Payment
import event.PaymentCancelledEvent
import event.PaymentCompletedEvent
import event.PaymentEvent
import event.PaymentFailedEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import valueobject.CreditHistoryId
import valueobject.TransactionType
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*


class PaymentDomainServiceImpl : PaymentDomainService {
    private val logger = KotlinLogging.logger {}

    override fun validateAndInitiatePayment(
        payment: Payment,
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCompletedEventDomainEventPublisher: DomainEventPublisher<PaymentCompletedEvent>,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {
        payment.validatePayment(failureMessages)
        payment.initializePayment()
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);

        if (failureMessages.isEmpty()) {
            logger.info { "Payment is initiated for order id: ${payment.orderId.value}" }
            payment.updateStatus(PaymentStatus.COMPLETED)
            return PaymentCompletedEvent(
                payment,
                ZonedDateTime.now(ZoneId.of(DomainConstants.UTC)),
                paymentCompletedEventDomainEventPublisher
            )
        } else {
            logger.info("Payment initiation is failed for order id: {}", payment.orderId.value)
            payment.updateStatus(PaymentStatus.FAILED)
            return PaymentFailedEvent(
                payment,
                ZonedDateTime.now(ZoneId.of(DomainConstants.UTC)),
                failureMessages,
                paymentFailedEventDomainEventPublisher
            )
        }
    }

    override fun validateAndCancelPayment(
        payment: Payment,
        creditEnty: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>,
        paymentCancelledEventDomainEventPublisher: DomainEventPublisher<PaymentCancelledEvent>,
        paymentFailedEventDomainEventPublisher: DomainEventPublisher<PaymentFailedEvent>
    ): PaymentEvent {
        TODO("Not yet implemented")
    }

    private fun validateCreditEntry(payment: Payment, creditEntry: CreditEntry, failureMessages: MutableList<String>) {
        if (payment.price.isGreaterThan(creditEntry.totalCreditAmount)) {
            logger.error { "Customer with id: ${payment.customerId.value} doesn't have enough credit for payment!" }
            failureMessages.add(
                "Customer with id=" + payment.customerId.value
                        + " doesn't have enough credit for payment!"
            );
        }
    }

    private fun subtractCreditEntry(payment: Payment, creditEntry: CreditEntry) {
        creditEntry.subtractCreditAmount(payment.price);
    }

    private fun updateCreditHistory(
        payment: Payment,
        creditHistories: MutableList<CreditHistory>,
        transactionType: TransactionType
    ) {
        creditHistories.add(
            CreditHistory(
                CreditHistoryId(UUID.randomUUID()),
                payment.customerId,
                payment.price,
                transactionType
            )
        )
    }

    private fun validateCreditHistory(
        creditEntry: CreditEntry,
        creditHistories: MutableList<CreditHistory>,
        failureMessages: MutableList<String>
    ) {
        val totalCreditHistory: Money = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT)
        val totalDebitHistory: Money = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT)

        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            logger.error { "Customer with id: ${creditEntry.customerId.value} doesn't have enough credit according to credit history" }

            failureMessages.add(
                ("Customer with id=" + creditEntry.customerId.value).toString() +
                        " doesn't have enough credit according to credit history!"
            )
        }

        if (!creditEntry.totalCreditAmount.equals(totalCreditHistory.subtract(totalDebitHistory))) {
            logger.error(
                "Credit history total is not equal to current credit for customer id: {}!",
                creditEntry.customerId.value
            )
            failureMessages.add(
                ("Credit history total is not equal to current credit for customer id: " +
                        creditEntry.customerId.value).toString() + "!"
            )
        }
    }

    private fun getTotalHistoryAmount(creditHistories: List<CreditHistory>, transactionType: TransactionType): Money {
        return creditHistories.stream()
            .filter { creditHistory: CreditHistory -> transactionType == creditHistory.transactionType }
            .map(CreditHistory::amount)
            .reduce(Money.ZERO) { obj: Money, money: Money -> obj.add(money) }
    }

    private fun addCreditEntry(payment: Payment, creditEntry: CreditEntry) {
        creditEntry.addCreditAmount(payment.price)
    }

}