package com.epam.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Offering {
    private final String name;
    private BigDecimal price;
    private final OfferingType type;

    public Offering(String name, BigDecimal price, OfferingType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Offering(Offering offering) {
        this(offering.getName(), offering.getPrice(), offering.getType());
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OfferingType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offering offering = (Offering) o;
        return Objects.equals(name, offering.name) && Objects.equals(price, offering.price) && type == offering.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, type);
    }
}