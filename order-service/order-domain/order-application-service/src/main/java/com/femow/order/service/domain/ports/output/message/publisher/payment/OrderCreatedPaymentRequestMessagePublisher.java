package com.femow.order.service.domain.ports.output.message.publisher.payment;

import com.femow.domain.event.publisher.DomainEventPublisher;
import com.femow.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
