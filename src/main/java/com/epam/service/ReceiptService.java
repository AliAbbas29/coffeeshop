package com.epam.service;

import com.epam.model.Product;

import java.math.BigDecimal;
import java.util.List;

/**
 * The ReceiptService interface provides a contract for generating receipts.
 * Implementations of this interface should define how receipts are generated.
 */
public interface ReceiptService {

    /**
     * Generates a receipt for the given list of products with their total cost.
     * The receipt should include the details of each product and the total cost of all products.
     *
     * @param products a list of products to include in the receipt
     * @param totalCost the total cost of all products
     * @return a string that represents the receipt
     */
    String generateReceipt(List<Product> products, BigDecimal totalCost);
}