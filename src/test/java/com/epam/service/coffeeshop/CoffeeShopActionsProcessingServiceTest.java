package com.epam.service.coffeeshop;

import com.epam.service.OrderService;
import com.epam.service.UserInputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.epam.message.Actions.ACTIONS;
import static com.epam.message.Actions.BACK;
import static com.epam.message.Actions.BONUS;
import static com.epam.message.Actions.MENU;
import static com.epam.message.Actions.ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CoffeeShopActionsProcessingServiceTest {
    private static final String INVALID_ACTION = "abcde";

    private UserInputService userInputService;
    private OrderService coffeeShopOrderService;

    private CoffeeShopActionsProcessingService coffeeShopActionsProcessingService;

    @BeforeEach
    public void setUp() {
        userInputService = mock(UserInputService.class);
        coffeeShopOrderService = mock(OrderService.class);
        coffeeShopActionsProcessingService = new CoffeeShopActionsProcessingService(userInputService, coffeeShopOrderService);
    }

    @Test
    public void testProcessActionsAndGetReceipt() {
        // When
        String orderResult = "Receipt: Small coffee - 2.55";
        when(userInputService.getUserInput()).thenReturn(ORDER);
        when(coffeeShopOrderService.processOrders()).thenReturn(orderResult);
        // Action
        String result = coffeeShopActionsProcessingService.processActionsAndGetReceipt();
        // Assert
        assertEquals(orderResult, result);
        verify(userInputService).getUserInput();
        verify(coffeeShopOrderService).processOrders();
    }

    @Test
    public void testProcessActionsAndGetNull_OrderAction() {
        // When
        when(userInputService.getUserInput()).thenReturn(ORDER);
        when(coffeeShopOrderService.processOrders()).thenReturn(BACK);
        // Action
        String result = coffeeShopActionsProcessingService.processActions();
        // Assert
        assertNull(result);
        verify(userInputService).getUserInput();
        verify(coffeeShopOrderService).processOrders();
    }

    @Test
    public void testProcessActions_MenuAction() {
        testProcessActionsAndGetNull(MENU);
    }

    @Test
    public void testProcessActions_BonusAction() {
        testProcessActionsAndGetNull(BONUS);
    }

    @Test
    public void testProcessActions_ActionsAction() {
        testProcessActionsAndGetNull(ACTIONS);
    }

    @Test
    public void testProcessActions_InvalidAction() {
        testProcessActionsAndGetNull(INVALID_ACTION);
    }

    private void testProcessActionsAndGetNull(String action) {
        // When
        when(userInputService.getUserInput()).thenReturn(action);
        // Action
        String result = coffeeShopActionsProcessingService.processActions();
        // Assert
        assertNull(result);
        verify(userInputService).getUserInput();
        verify(coffeeShopOrderService, never()).processOrders();
    }
}