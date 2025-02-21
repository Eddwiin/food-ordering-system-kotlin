package com.food.ordering.system.kotlin.order.service.domain

import com.food.ordering.system.kotlin.domain.valueobject.*
import com.food.ordering.system.kotlin.order.service.domain.dto.create.CreateOrderCommand
import com.food.ordering.system.kotlin.order.service.domain.dto.create.OrderAddress
import com.food.ordering.system.kotlin.order.service.domain.dto.create.OrderItem
import com.food.ordering.system.kotlin.order.service.domain.entity.Customer
import com.food.ordering.system.kotlin.order.service.domain.entity.Order
import com.food.ordering.system.kotlin.order.service.domain.entity.Product
import com.food.ordering.system.kotlin.order.service.domain.entity.Restaurant
import com.food.ordering.system.kotlin.order.service.domain.mapper.OrderDataMapper
import com.food.ordering.system.kotlin.order.service.domain.ports.input.service.OrderApplicationService
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.CustomerRepository
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.OrderRepository
import com.food.ordering.system.kotlin.order.service.domain.ports.output.repository.RestaurantRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = [OrderTestConfiguration::class])
class OrderApplicationServiceTest(
    @Autowired
    private val orderApplicationService: OrderApplicationService,

    @Autowired
    private val orderDataMapper: OrderDataMapper,

    @Autowired
    private val orderRepository: OrderRepository,

    @Autowired
    private val customerRepository: CustomerRepository,

    @Autowired
    private val restaurantRepository: RestaurantRepository,

    @MockBean
    private var orderCreateCommandHandler: OrderCreateCommandHandler,

    @MockBean
    private var orderTrackCommandHandler: OrderTrackCommandHandler,

    private val createOrderCommand: CreateOrderCommand,
    private val createOrderCommandWrongPrice: CreateOrderCommand,
    private val createOrderCommandWrongProduct: CreateOrderCommand,
    private val CUSTOMER_ID: UUID = UUID.fromString("e8f28a3e-1e42-4d8e-b9c7-667b9d6f0df6"),
    private val RESTAURANT_ID: UUID = UUID.fromString("452a8b89-0f56-4ccd-a8a3-bb5a07699454"),
    private val PRODUCT_ID: UUID = UUID.fromString("72df4770-6aee-4f7a-97b7-98032234117c"),
    private val ORDER_ID: UUID = UUID.fromString("94d27beb-7f46-49a1-a2b2-2318e325eaf9"),
    private val PRICE: BigDecimal = BigDecimal("200.00")

) {

    @BeforeAll
    fun init() {
        val createOrderCommand = CreateOrderCommand.builder()
            .customerId(CUSTOMER_ID)
            .restaurantId(RESTAURANT_ID)
            .address(
                OrderAddress.builder()
                    .street("street_1")
                    .postalCode("1000AB")
                    .city("Paris")
                    .build()
            )
            .price(PRICE)
            .items(
                listOf(
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(1)
                        .price(BigDecimal("50.00"))
                        .subTotal(BigDecimal("50.00"))
                        .build(),
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(3)
                        .price(BigDecimal("50.00"))
                        .subTotal(BigDecimal("150.00"))
                        .build()
                )
            )
            .build()

        val createOrderCommandWrongPrice = CreateOrderCommand.builder()
            .customerId(CUSTOMER_ID)
            .restaurantId(RESTAURANT_ID)
            .address(
                OrderAddress.builder()
                    .street("street_1")
                    .postalCode("1000AB")
                    .city("Paris")
                    .build()
            )
            .price(BigDecimal("250.00"))
            .items(
                listOf(
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(1)
                        .price(BigDecimal("50.00"))
                        .subTotal(BigDecimal("50.00"))
                        .build(),
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(3)
                        .price(BigDecimal("50.00"))
                        .subTotal(BigDecimal("150.00"))
                        .build()
                )
            )
            .build()

        val createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
            .customerId(CUSTOMER_ID)
            .restaurantId(RESTAURANT_ID)
            .address(
                OrderAddress.builder()
                    .street("street_1")
                    .postalCode("1000AB")
                    .city("Paris")
                    .build()
            )
            .price(BigDecimal("210.00"))
            .items(
                listOf(
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(1)
                        .price(BigDecimal("60.00"))
                        .subTotal(BigDecimal("60.00"))
                        .build(),
                    OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(3)
                        .price(BigDecimal("50.00"))
                        .subTotal(BigDecimal("150.00"))
                        .build()
                )
            )
            .build()

        val customer = Customer();
        customer.id = CustomerId(CUSTOMER_ID)

        val restaurantResponse = Restaurant.Builder()
            .restaurantId(RestaurantId(RESTAURANT_ID))
            .products(
                listOf(
                    Product(
                        ProductId(PRODUCT_ID), "product-1",
                        Money(BigDecimal("50.0"))
                    ),
                    Product(ProductId(PRODUCT_ID), "product-2", Money(BigDecimal("50.0")))
                )
            )
            .active(true)
            .build();

        val order = orderDataMapper.createOrderCommandToOrder(createOrderCommand)
        order.id = OrderId(ORDER_ID)

        Mockito.`when`(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer))
        Mockito.`when`(
            restaurantRepository.findRestaurantInformation(
                orderDataMapper.createOrderCommandToRestaurant(
                    createOrderCommand
                )
            )
        ).thenReturn(Optional.of(restaurantResponse))
        Mockito.`when`(orderRepository.save(any<Order>())).thenReturn(order)
    }

    @Test
    fun testCreateOrder() {
        val createOrderResponse = orderApplicationService.createOrder(createOrderCommand)
        assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus)
        assertEquals("Order created successfully", createOrderResponse.message)
        assertNotNull(createOrderResponse.orderTrackingId)
    }
}