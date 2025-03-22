package com.femow.order.service.dataaccess.custom.mapper;

import com.femow.domain.valueobject.CustomerId;
import com.femow.order.service.dataaccess.custom.entity.CustomerEntity;
import com.femow.order.service.domain.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {

    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()));
    }
}
