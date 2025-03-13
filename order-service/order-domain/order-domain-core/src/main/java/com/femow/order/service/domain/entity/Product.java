package com.femow.order.service.domain.entity;

import com.femow.domain.entity.BaseEntity;
import com.femow.domain.valueobject.Money;
import com.femow.domain.valueobject.ProductId;

import java.util.Objects;

public class Product extends BaseEntity<ProductId> {

    private String name;
    private Money price;

     public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
     }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Product product = (Product) object;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
