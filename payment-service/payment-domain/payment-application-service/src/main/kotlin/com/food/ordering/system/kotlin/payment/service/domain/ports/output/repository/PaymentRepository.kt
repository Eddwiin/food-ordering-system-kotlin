package com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository

import entity.Payment
import java.util.*

interface PaymentRepository {
    fun save(payment: Payment);
    fun findByOrderId(orderId: UUID): Payment?
}