package com.epam.service;

import com.epam.model.Product;

import java.util.List;

/**
 * The ProductsService interface provides a contract for a service that retrieves product details.
 * Implementations of this interface are expected to provide a concrete implementation of these operations.
 */
public interface ProductsService {

    /**
     * Retrieves a list of Product objects based on the provided shopping list.
     * Each string in the shopping list should correspond to a unique product.
     *
     * @param shoppingList a list of product names that the customer wants to purchase
     * @return a list of Product objects that correspond to the items in the shopping list
     */
    List<Product> getProducts(List<String> shoppingList);
}