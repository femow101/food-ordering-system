package com.femow.order.service.domain.entity;

import com.femow.domain.entity.AggregateRoot;
import com.femow.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

    public Customer() {

    }

    public Customer(CustomerId customerId) {
        super.setId(customerId);
    }
}
