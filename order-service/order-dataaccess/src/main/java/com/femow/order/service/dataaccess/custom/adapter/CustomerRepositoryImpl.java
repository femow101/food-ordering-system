package com.femow.order.service.dataaccess.custom.adapter;

import com.femow.order.service.dataaccess.custom.mapper.CustomerDataAccessMapper;
import com.femow.order.service.dataaccess.custom.repository.CustomerJpaRepository;
import com.femow.order.service.domain.entity.Customer;
import com.femow.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerDataAccessMapper customerDataAccessMapper;
    private final CustomerJpaRepository customerJpaRepository;

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
