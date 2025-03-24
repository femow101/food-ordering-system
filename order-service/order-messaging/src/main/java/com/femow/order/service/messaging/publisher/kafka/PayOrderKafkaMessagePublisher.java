package com.femow.order.service.messaging.publisher.kafka;

import com.femow.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import com.femow.kafka.producer.service.KafkaProducer;
import com.femow.order.service.domain.config.OrderServiceConfigData;
import com.femow.order.service.domain.event.OrderCreatedEvent;
import com.femow.order.service.domain.event.OrderPaidEvent;
import com.femow.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.femow.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {

    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;
    private final OrderServiceConfigData orderServiceConfigData;

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();

        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(domainEvent);

            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getRestaurantApprovalResponseTopicName(),
                            restaurantApprovalRequestAvroModel,
                            orderId,
                            "RestaurantApprovalRequestAvroModel")
            );
            log.info("RestaurantApprovalRequestAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception ex) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message to kafka with order id: {}, error: {}",
                    orderId, ex.getMessage());
        }
    }
}
