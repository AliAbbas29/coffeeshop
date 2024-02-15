package com.epam.service.coffeeshop;

import com.epam.util.ExcludeJacocoScanGenerated;
import com.epam.constants.Actions;
import com.epam.constants.coffeeshop.Messages;
import com.epam.service.ActionsProcessingService;
import com.epam.service.OrderService;
import com.epam.service.UserInputService;

import static com.epam.constants.Actions.ACTIONS;
import static com.epam.constants.Actions.BACK;
import static com.epam.constants.Actions.ORDER;

/**
 * The CoffeeShopActionsProcessingService class provides an implementation of the ActionsProcessingService interface.
 * It handles the processing of user actions in the coffee shop.
 */
public class CoffeeShopActionsProcessingService implements ActionsProcessingService {
    private final UserInputService userInputService;
    private final OrderService coffeeShopOrderService;

    public CoffeeShopActionsProcessingService(UserInputService userInputService, OrderService coffeeShopOrderService) {
        this.userInputService = userInputService;
        this.coffeeShopOrderService = coffeeShopOrderService;
    }

    /**
     * Processes user actions until an order is placed.
     * The available actions include viewing the menu, viewing the bonus program, and placing an order.
     *
     * @return a string that represents the receipt for the order
     */
    @ExcludeJacocoScanGenerated
    public String processActionsAndGetReceipt() {
        System.out.println(Messages.ACTIONS);
        String result;
        do {
            result = processActions();
        } while (result == null);
        return result;
    }

    String processActions() {
        String action = userInputService.getUserInput();
        printMessage(action);
        if (ORDER.equalsIgnoreCase(action)) {
            String orderResult = coffeeShopOrderService.processOrders();
            if (BACK.equalsIgnoreCase(orderResult)) {
                System.out.println(Messages.ACTIONS);
                return null;
            }
            return orderResult;
        } else if (!ACTIONS.equalsIgnoreCase(action)) {
            System.out.println(Messages.HELP);
        }
        return null;
    }

    private void printMessage(String action) {
        String message = getMessage(action);
        System.out.println(message);
    }

    private String getMessage(String userInput) {
        return switch (userInput.toLowerCase()) {
            case Actions.MENU -> Messages.MENU;
            case Actions.BONUS -> Messages.BONUS_PROGRAM;
            case Actions.ORDER -> Messages.ORDER_START;
            case Actions.ACTIONS -> Messages.ACTIONS;
            default -> Messages.INVALID_ACTION;
        };
    }
}