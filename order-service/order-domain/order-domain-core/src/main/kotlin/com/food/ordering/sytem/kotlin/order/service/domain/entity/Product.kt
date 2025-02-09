package com.food.ordering.sytem.kotlin.order.service.domain.entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.ProductId

class Product(
    val productId: ProductId,
    var name: String? = null,
    var price: Money? = null,
) : BaseEntity<ProductId>() {
    init {
        this.id = productId
    }
}