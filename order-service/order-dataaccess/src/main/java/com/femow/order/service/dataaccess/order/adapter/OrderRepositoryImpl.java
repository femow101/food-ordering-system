package com.femow.order.service.dataaccess.order.adapter;

import com.femow.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import com.femow.order.service.dataaccess.order.repository.OrderJpaRepository;
import com.femow.order.service.domain.entity.Order;
import com.femow.order.service.domain.ports.output.repository.OrderRepository;
import com.femow.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
