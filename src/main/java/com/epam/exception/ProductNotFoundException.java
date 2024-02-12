package com.epam.exception;

/**
 * This exception is thrown when a requested product is not found in the store.
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a new ProductNotFoundException with a detailed message.
     *
     * @param productName the name of the product that could not be found
     */
    public ProductNotFoundException(String productName) {
        super("Product " + productName + " is not available this moment. We are working on increasing our store assortment!");
    }
}