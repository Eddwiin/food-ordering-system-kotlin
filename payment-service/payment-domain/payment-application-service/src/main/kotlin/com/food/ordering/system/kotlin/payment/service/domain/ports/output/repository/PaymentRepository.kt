package com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository

import com.food.ordering.system.kotlin.payment.service.domain.core.entity.Payment
import java.util.*

interface PaymentRepository {
    fun save(payment: Payment): Payment?;
    fun findByOrderId(orderId: UUID): Payment?
}