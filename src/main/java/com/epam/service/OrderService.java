package com.epam.service;

/**
 * The OrderService interface provides a contract for processing orders.
 * Implementations of this interface should define how orders are processed.
 */
public interface OrderService {
    /**
     * Processes orders based on implementation-specific details.
     *
     * @return a string that represents the result of the order processing.
     * This could be a receipt, a confirmation message, or any other relevant information.
     */
    String processOrders();
}