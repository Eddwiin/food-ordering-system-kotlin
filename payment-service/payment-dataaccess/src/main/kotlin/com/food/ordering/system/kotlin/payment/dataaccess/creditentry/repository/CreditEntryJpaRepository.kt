package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.repository

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.entity.CreditEntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditEntryJpaRepository : JpaRepository<CreditEntryEntity, UUID> {
    fun findByCustomerId(customerId: UUID): CreditEntryEntity?
}