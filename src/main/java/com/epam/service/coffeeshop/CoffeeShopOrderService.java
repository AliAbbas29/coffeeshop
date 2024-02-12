package com.epam.service.coffeeshop;

import com.epam.exception.ProductNotFoundException;
import com.epam.model.Product;
import com.epam.service.CostsService;
import com.epam.service.OrderService;
import com.epam.service.ProductsService;
import com.epam.service.ReceiptService;
import com.epam.service.UserInputService;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.message.Actions.BACK;
import static com.epam.message.coffeeshop.Messages.ORDER_BACK;
import static com.epam.message.coffeeshop.Messages.ORDER_NOT_AVAILABLE;

/**
 * The CoffeeShopOrderService class provides an implementation of the OrderService interface.
 * It handles the processing of orders in the coffee shop.
 */
public class CoffeeShopOrderService implements OrderService {
    private final UserInputService userInputService;
    private final ProductsService productsService;
    private final CostsService costsService;
    private final ReceiptService receiptService;

    /**
     * Constructs a new CoffeeShopOrderService with the given UserInputService, ProductsService, CostsService, and ReceiptService.
     *
     * @param userInputService the service to use for handling user input
     * @param productsService  the service to use for retrieving product details
     * @param costsService     the service to use for calculating the total cost of an order
     * @param receiptService   the service to use for generating receipts
     */
    public CoffeeShopOrderService(UserInputService userInputService, ProductsService productsService,
                                  CostsService costsService, ReceiptService receiptService) {
        this.userInputService = userInputService;
        this.productsService = productsService;
        this.costsService = costsService;
        this.receiptService = receiptService;
    }

    /**
     * Processes an orders based on user input. The method continues to prompt the user for input until a valid order is placed.
     *
     * @return a string that represents the receipt for the order, or a message indicating that the order was not placed
     */
    public String processOrders() {
        String result;
        do {
            result = processSingleOrder();
        } while (result == null);
        return result;
    }

    String processSingleOrder() {
        try {
            System.out.println(ORDER_BACK);
            String userInput = userInputService.getUserInput();
            return getOrderResult(userInput);
        } catch (ProductNotFoundException exception) {
            System.out.println(exception.getMessage());
            System.out.println(ORDER_NOT_AVAILABLE);
        }
        return null;
    }

    private String getOrderResult(String userInput) {
        if (BACK.equalsIgnoreCase(userInput)) {
            return userInput;
        }
        List<String> orderedItems = userInputService.getOrderedItemsByUserInput(userInput);
        List<Product> products = productsService.getProducts(orderedItems);
        BigDecimal totalCosts = costsService.calculateTotalCosts(products);
        return receiptService.generateReceipt(products, totalCosts);
    }
}