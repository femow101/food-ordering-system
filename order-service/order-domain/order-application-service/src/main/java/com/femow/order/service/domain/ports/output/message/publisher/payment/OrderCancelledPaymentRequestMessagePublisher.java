package com.femow.order.service.domain.ports.output.message.publisher.payment;

import com.femow.domain.event.publisher.DomainEventPublisher;
import com.femow.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
