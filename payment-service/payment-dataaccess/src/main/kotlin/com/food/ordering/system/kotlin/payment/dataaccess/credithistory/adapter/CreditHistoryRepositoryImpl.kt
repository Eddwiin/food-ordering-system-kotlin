package com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.adapter

import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper
import com.food.ordering.system.kotlin.com.food.ordering.system.kotlin.payment.dataaccess.credithistory.repository.CreditHistoryJpaRepository
import com.food.ordering.system.kotlin.domain.valueobject.CustomerId
import com.food.ordering.system.kotlin.payment.service.domain.core.entity.CreditHistory
import com.food.ordering.system.kotlin.payment.service.domain.ports.output.repository.CreditHistoryRepository
import org.springframework.stereotype.Component


@Component
class CreditHistoryRepositoryImpl(
    val creditHistoryJpaRepository: CreditHistoryJpaRepository,
    val creditHistoryDataAccessMapper: CreditHistoryDataAccessMapper
) : CreditHistoryRepository {
    override fun save(creditHistory: CreditHistory): CreditHistory {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(
            creditHistoryJpaRepository
                .save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory))
        );
    }

    override fun findByCustomerId(customerId: CustomerId): MutableList<CreditHistory>? {
        return creditHistoryJpaRepository.findByCustomerId(customerId.value!!)
            ?.map { creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(it) }
            ?.toMutableList()
    }
}