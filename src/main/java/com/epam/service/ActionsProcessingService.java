package com.epam.service;

/**
 * The ActionsProcessingService interface provides a contract for a service that processes user actions.
 * Implementations of this interface are expected to provide a concrete implementation of these operations.
 */
public interface ActionsProcessingService {

    /**
     * Processes user actions and returns a receipt.
     * The specific actions that can be processed, and the format of the receipt, depend on the implementation.
     *
     * @return a string that represents the receipt
     */
    String processActionsAndGetReceipt();
}