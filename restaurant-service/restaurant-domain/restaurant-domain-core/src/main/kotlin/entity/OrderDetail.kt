package entity

import com.food.ordering.system.kotlin.domain.entity.BaseEntity
import com.food.ordering.system.kotlin.domain.valueobject.Money
import com.food.ordering.system.kotlin.domain.valueobject.OrderId
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus

class OrderDetail(
    val orderStatus: OrderStatus? = null,
    val totalAmount: Money? = null,
    val products: List<Product>? = null,
    var orderId: OrderId? = null

) : BaseEntity<OrderId>()