package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.repository

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.entity.CreditHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditHistoryJpaRepository : JpaRepository<CreditHistoryEntity, UUID> {
    fun findByCustomerId(customerId: UUID): MutableList<CreditHistoryEntity>?
}