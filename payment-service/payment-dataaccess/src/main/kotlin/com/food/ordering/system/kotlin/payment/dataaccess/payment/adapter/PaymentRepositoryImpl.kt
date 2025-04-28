package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.adapter

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.mapper.PaymentDataAccessMapper
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.repository.PaymentJpaRepository
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.PaymentRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class PaymentRepositoryImpl(
    val paymentJpaRepository: PaymentJpaRepository? = null,
    val paymentDataAccessMapper: PaymentDataAccessMapper? = null
) : PaymentRepository {
    override fun save(payment: Payment): Payment? {
        return paymentDataAccessMapper
            ?.paymentEntityToPayment(
                paymentJpaRepository
                    ?.save(paymentDataAccessMapper.paymentToPaymentEntity(payment))
            );
    }

    override fun findByOrderId(orderId: UUID): Payment? {
        return paymentDataAccessMapper
            ?.paymentEntityToPayment(paymentJpaRepository?.findByOrderId(orderId))

    }
}