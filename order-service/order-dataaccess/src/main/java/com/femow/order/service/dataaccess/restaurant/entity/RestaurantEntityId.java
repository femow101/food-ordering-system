package com.femow.order.service.dataaccess.restaurant.entity;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntityId implements Serializable {

    private UUID restaurantId;
    private UUID productId;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        RestaurantEntityId that = (RestaurantEntityId) object;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, productId);
    }
}
