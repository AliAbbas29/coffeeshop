package com.epam.service.coffeeshop;

import com.epam.model.Product;
import com.epam.service.ReceiptService;

import java.math.BigDecimal;
import java.util.List;

/**
 * The CoffeeShopReceiptService class provides an implementation of the ReceiptService interface.
 * It generates a receipt for a list of products with their total cost.
 */
public class CoffeeShopReceiptService implements ReceiptService {

    /**
     * Generates a receipt for the given list of products with their total cost.
     * The receipt includes the details of each product and the total cost of all products.
     *
     * @param products a list of products to include in the receipt
     * @param totalCost the total cost of all products
     * @return a string that represents the receipt
     */
    public String generateReceipt(List<Product> products, BigDecimal totalCost) {
        StringBuilder receipt = new StringBuilder("===== Receipt =====\n");
        products.forEach(product ->
                receipt.append(product.toString()).append(" CHF\n"));

        receipt.append("-------------------\n")
                .append("Total Cost: ").append(totalCost).append(" CHF\n")
                .append("===================");
        return receipt.toString();
    }
}