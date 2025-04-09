package entity

import com.food.ordering.system.kotlin.domain.entity.AggregateRoot
import com.food.ordering.system.kotlin.domain.valueobject.OrderStatus
import com.food.ordering.system.kotlin.domain.valueobject.RestaurantId


class Restaurant(
    val orderApproval: OrderApproval? = null,
    var active: Boolean = false,
    val orderDetail: OrderDetail? = null,
    val restaurantId: RestaurantId
) : AggregateRoot<RestaurantId>() {
    fun validateOrder(failureMessages: MutableList<String>?) {

        if (orderDetail?.orderStatus != OrderStatus.PAID) {
            failureMessages?.add("Payment is not completed for order: ${orderDetail?.id}")
        }

        val totalAmount = orderDetail?.products?.map { product ->
            if (!product.available) {
                failureMessages?.add("Product with id: ${product.id?.value} is not available")
            }
            product.price?.multiply(product.quantity)
        }?.reduce { acc, money -> acc?.add(money!!) }

        if (totalAmount != orderDetail?.totalAmount) {
            failureMessages?.add("Price total is not correct for order: ${orderDetail?.id}")
        }
    }
}