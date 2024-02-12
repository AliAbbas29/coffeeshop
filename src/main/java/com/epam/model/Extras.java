package com.epam.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Extras {
    private String name;
    private BigDecimal price;

    public Extras(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Extras(Extras extras) {
        this(extras.getName(), extras.getPrice());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Extras extras = (Extras) o;
        return Objects.equals(name, extras.name) && Objects.equals(price, extras.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}