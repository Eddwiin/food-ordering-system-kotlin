package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.adapter

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.mapper.CreditEntryDataAccessMapper
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.creditentry.repository.CreditEntryJpaRepository
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditEntry
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.CreditEntryRepository
import org.springframework.stereotype.Component

@Component
class CreditEntryRepositoryImpl(
    val creditEntryJpaRepository: CreditEntryJpaRepository,
    val creditEntryDataAccessMapper: CreditEntryDataAccessMapper
) : CreditEntryRepository {
    override fun save(creditEntry: CreditEntry): CreditEntry {
        return creditEntryDataAccessMapper
            .creditEntryEntityToCreditEntry(
                creditEntryJpaRepository
                    .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry))
            );
    }

    override fun findByCustomerId(customerId: CustomerId): CreditEntry? {
        return creditEntryDataAccessMapper.creditEntryEntityToCreditEntry(
            creditEntryJpaRepository
                .findByCustomerId(customerId.value!!)
        )

    }
}