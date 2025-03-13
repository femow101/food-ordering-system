package com.femow.order.service.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class StreetAddress {

    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID id, String street, String postalCode, String city) {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreetAddress that = (StreetAddress) o;
        return this.street.equals(that.street)
            && this.postalCode.equals(that.postalCode)
            && this.city.equals(that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street, this.postalCode, this.city);
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet() {
        return street;
    }

    public UUID getId() {
        return id;
    }
}
