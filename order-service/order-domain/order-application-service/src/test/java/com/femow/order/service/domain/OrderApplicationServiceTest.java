package com.femow.order.service.domain;

import com.femow.domain.valueobject.*;
import com.femow.order.service.domain.dto.create.CreateOrderCommand;
import com.femow.order.service.domain.dto.create.CreateOrderResponse;
import com.femow.order.service.domain.dto.create.OrderAddress;
import com.femow.order.service.domain.dto.create.OrderItem;
import com.femow.order.service.domain.entity.Customer;
import com.femow.order.service.domain.entity.Order;
import com.femow.order.service.domain.entity.Product;
import com.femow.order.service.domain.entity.Restaurant;
import com.femow.order.service.domain.mapper.OrderDataMapper;
import com.femow.order.service.domain.ports.input.service.OrderApplicationService;
import com.femow.order.service.domain.ports.output.repository.CustomerRepository;
import com.femow.order.service.domain.ports.output.repository.OrderRepository;
import com.femow.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("5a62e224-4575-41a0-b17d-d6e67de91abe");
    private final UUID RESTAURANT_ID = UUID.fromString("5a62e224-4575-41a0-b17d-d6e67de92abe");
    private final UUID PRODUCT_ID = UUID.fromString("5a62e224-4575-41a0-b17d-d6e67de93abe");
    private final UUID ORDER_ID = UUID.fromString("5a62e224-4575-41a0-b17d-d6e67de94abe");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        OrderAddress orderAddress = OrderAddress.builder()
                .street("street_1")
                .postalCode("1000AB")
                .city("Paris")
                .build();

        createOrderCommand = CreateOrderCommand.builder()
            .customerId(CUSTOMER_ID)
            .restaurantId(RESTAURANT_ID)
            .address(orderAddress)
            .price(PRICE)
            .items(List.of(OrderItem.builder()
                            .productId(PRODUCT_ID)
                            .quantity(1)
                            .price(new BigDecimal("50.00"))
                            .subTotal(new BigDecimal("50.00"))
                            .build(),
                    OrderItem.builder()
                            .productId(PRODUCT_ID)
                            .quantity(3)
                            .price(new BigDecimal("50.00"))
                            .subTotal(new BigDecimal("150.00"))
                            .build()))
            .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(orderAddress)
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(orderAddress)
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
            .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    public void testeCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order Created Successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }
}
