package com.femow.order.service.domain;

import com.femow.order.service.domain.dto.create.CreateOrderCommand;
import com.femow.order.service.domain.dto.create.CreateOrderResponse;
import com.femow.order.service.domain.event.OrderCreatedEvent;
import com.femow.order.service.domain.mapper.OrderDataMapper;
import com.femow.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateCommandHandler {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());

        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);

        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),  "Order Created Successfully");
    }
}
