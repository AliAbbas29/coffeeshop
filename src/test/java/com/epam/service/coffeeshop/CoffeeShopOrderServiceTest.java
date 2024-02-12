package com.epam.service.coffeeshop;


import com.epam.exception.ProductNotFoundException;
import com.epam.model.Product;
import com.epam.service.CostsService;
import com.epam.service.ProductsService;
import com.epam.service.ReceiptService;
import com.epam.service.UserInputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.message.Actions.BACK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CoffeeShopOrderServiceTest {
    private UserInputService userInputService;
    private ProductsService productsService;
    private CostsService costsService;
    private ReceiptService receiptService;

    private CoffeeShopOrderService coffeeShopOrderService;

    @BeforeEach
    public void setUp() {
        userInputService = mock(UserInputService.class);
        productsService = mock(ProductsService.class);
        costsService = mock(CostsService.class);
        receiptService = mock(ReceiptService.class);
        coffeeShopOrderService = new CoffeeShopOrderService(userInputService, productsService, costsService, receiptService);
    }

    @Test
    public void testProcessOrder() {
        // When
        String userInput = "Small coffee with milk";
        List<String> orderedItems = List.of("Small coffee", "milk");
        List<Product> products = List.of(mock(Product.class));
        BigDecimal totalCosts = new BigDecimal("3.00");
        String receipt = "Receipt: Small coffee with milk - 3.00";

        when(userInputService.getUserInput()).thenReturn(userInput);
        when(userInputService.getOrderedItemsByUserInput(userInput)).thenReturn(orderedItems);
        when(productsService.getProducts(orderedItems)).thenReturn(products);
        when(costsService.calculateTotalCosts(products)).thenReturn(totalCosts);
        when(receiptService.generateReceipt(products, totalCosts)).thenReturn(receipt);
        // Action
        String result = coffeeShopOrderService.processOrders();
        // Assert
        assertEquals(receipt, result);
        verify(userInputService).getUserInput();
        verify(userInputService).getOrderedItemsByUserInput(userInput);
        verify(productsService).getProducts(orderedItems);
        verify(costsService).calculateTotalCosts(products);
        verify(receiptService).generateReceipt(products, totalCosts);
    }

    @Test
    public void testProcessOrderForBackUserInput() {
        // When
        when(userInputService.getUserInput()).thenReturn(BACK);
        // Action
        String result = coffeeShopOrderService.processOrders();
        // Assert
        assertEquals(BACK, result);
        verify(userInputService).getUserInput();
        verify(userInputService, never()).getOrderedItemsByUserInput(any());
        verify(productsService, never()).getProducts(any());
        verify(costsService, never()).calculateTotalCosts(any());
        verify(receiptService, never()).generateReceipt(any(), any());
    }

    @Test
    public void testProcessOrderWhenThrows() {
        // When
        String userInput = "Small coffee with milk";
        List<String> orderedItems = List.of("Small coffee", "milk");
        when(userInputService.getUserInput()).thenReturn(userInput);
        when(userInputService.getOrderedItemsByUserInput(userInput)).thenReturn(orderedItems);
        when(productsService.getProducts(orderedItems)).thenThrow(ProductNotFoundException.class);
        // Action
        String result = coffeeShopOrderService.processSingleOrder();
        // Assert
        assertNull(result);
        verify(userInputService).getUserInput();
        verify(userInputService).getOrderedItemsByUserInput(userInput);
        verify(productsService).getProducts(orderedItems);
        verify(costsService, never()).calculateTotalCosts(any());
        verify(receiptService, never()).generateReceipt(any(), any());
    }
}