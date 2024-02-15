package com.epam;


import com.epam.model.User;
import com.epam.service.ActionsProcessingService;
import com.epam.service.BonusesService;
import com.epam.service.CostsService;
import com.epam.service.OrderService;
import com.epam.service.ProductsService;
import com.epam.service.ReceiptService;
import com.epam.service.UserInputService;
import com.epam.service.coffeeshop.CoffeeShopActionsProcessingService;
import com.epam.service.coffeeshop.CoffeeShopBonusesService;
import com.epam.service.coffeeshop.CoffeeShopCostsService;
import com.epam.service.coffeeshop.CoffeeShopOrderService;
import com.epam.service.coffeeshop.CoffeeShopProductsService;
import com.epam.service.coffeeshop.CoffeeShopReceiptService;
import com.epam.service.coffeeshop.CoffeeShopUserInputService;

import static com.epam.constants.coffeeshop.Messages.BONUSES;
import static com.epam.constants.coffeeshop.Messages.ORDER_FINISH;
import static com.epam.constants.coffeeshop.Messages.USER_MESSAGE;
import static com.epam.constants.coffeeshop.Messages.WELCOME_MESSAGE;

public class Main {
    private static final UserInputService userInputService = new CoffeeShopUserInputService();
    private static final BonusesService bonusesService = new CoffeeShopBonusesService();
    private static final ProductsService productsService = new CoffeeShopProductsService();
    private static final CostsService costsService = new CoffeeShopCostsService(bonusesService);
    private static final ReceiptService receiptService = new CoffeeShopReceiptService();
    private static final OrderService orderService = new CoffeeShopOrderService(userInputService, productsService, costsService, receiptService);
    private static final ActionsProcessingService processingService = new CoffeeShopActionsProcessingService(userInputService, orderService);

    public static void main(String[] args) {
        welcomeUser();
        informAboutBonuses();
        processUserActions();
    }

    private static void welcomeUser() {
        System.out.println(WELCOME_MESSAGE);
        System.out.println(USER_MESSAGE);
        String userName = userInputService.getUserInput();
        User.setCurrentUser(userName);
    }

    private static void informAboutBonuses() {
        String userName = User.getCurrentUser();
        int bonusesCount = bonusesService.getBonusPoints(userName);
        System.out.printf(BONUSES, userName, bonusesCount);
    }

    private static void processUserActions() {
        String receipt = processingService.processActionsAndGetReceipt();
        System.out.println(receipt);
        System.out.println(ORDER_FINISH);
    }
}