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

import static com.epam.message.coffeeshop.Messages.ORDER_FINISH;
import static com.epam.message.coffeeshop.Messages.USER_MESSAGE;
import static com.epam.message.coffeeshop.Messages.WELCOME_MESSAGE;

public class Main {
    public static void main(String[] args) {
        UserInputService userInputService = new CoffeeShopUserInputService();
        ProductsService productsService = new CoffeeShopProductsService();
        BonusesService bonusesService = new CoffeeShopBonusesService();
        CostsService costsService = new CoffeeShopCostsService(bonusesService);
        ReceiptService receiptService = new CoffeeShopReceiptService();
        OrderService orderService = new CoffeeShopOrderService(userInputService, productsService, costsService, receiptService);
        ActionsProcessingService processingService = new CoffeeShopActionsProcessingService(userInputService, orderService);

        System.out.println(WELCOME_MESSAGE);
        System.out.println(USER_MESSAGE);
        String userName = userInputService.getUserInput();
        User.setCurrentUser(userName);
        int bonusesCount = bonusesService.getBonusPoints(userName);
        System.out.println("The user " + userName + " have " + bonusesCount + " bonus points.");

        String receipt = processingService.processActionsAndGetReceipt();
        System.out.println(receipt);
        System.out.println(ORDER_FINISH);
    }
}