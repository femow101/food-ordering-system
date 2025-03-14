package com.femow.order.service.domain.ports.output.message.publisher.restaurantapproval;

import com.femow.domain.event.publisher.DomainEventPublisher;
import com.femow.order.service.domain.event.OrderCreatedEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
