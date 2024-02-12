package com.epam.service;

import com.epam.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * The CostsService interface provides a contract for a service that calculates the total cost of a list of products.
 * Implementations of this interface are expected to provide a concrete implementation of these operations.
 */
public interface CostsService {

    /**
     * Calculates the total cost of the given list of products.
     * The specific rules for calculating the total cost, such as applying discounts or special offers, depend on the implementation.
     *
     * @param products a list of products to calculate the total cost for
     * @return the total cost of the products
     */
    BigDecimal calculateTotalCosts(List<Product> products);
}