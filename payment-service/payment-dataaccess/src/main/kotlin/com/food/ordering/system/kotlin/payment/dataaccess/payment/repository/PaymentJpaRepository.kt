package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.repository

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.payment.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PaymentJpaRepository : JpaRepository<PaymentEntity, UUID> {
    fun findByOrderId(orderId: UUID?): PaymentEntity?
}