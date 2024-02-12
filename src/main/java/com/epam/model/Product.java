package com.epam.model;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;

public record Product(Offering offering, Extras extras) {

    public BigDecimal getPrice() {
        BigDecimal totalPrice = offering.getPrice();
        if (nonNull(extras)) {
            totalPrice = totalPrice.add(extras.getPrice());
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        if (nonNull(extras)) {
            return offering.getName() + " with " + extras.getName() + ": " + getPrice();
        }
        return offering.getName() + ": " + offering.getPrice();
    }
}