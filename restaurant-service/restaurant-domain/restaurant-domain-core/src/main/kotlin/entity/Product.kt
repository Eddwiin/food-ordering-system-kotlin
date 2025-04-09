package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.ProductId

class Product(
    var name: String? = null,
    var price: Money? = null,
    val quantity: Int = 0,
    var available: Boolean = false,
    productId: ProductId? = null
) : BaseEntity<ProductId>()