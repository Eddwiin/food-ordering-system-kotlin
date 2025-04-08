package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.ProductId

class Product(
    val name: String? = null,
    val price: Money? = null,
    val quantity: Int = 0,
    val available: Boolean = false,
    productId: ProductId? = null
) : BaseEntity<ProductId>()