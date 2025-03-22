package com.femow.order.service.dataaccess.custom.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_customer_m_view", schema = "customer")
public class CustomerEntity {

    @Id
    private UUID id;
}
