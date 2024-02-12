package com.epam.service.coffeeshop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeShopUserInputServiceTest {
    private CoffeeShopUserInputService coffeeShopUserInputService;

    @BeforeEach
    public void setUp() {
        coffeeShopUserInputService = new CoffeeShopUserInputService();
    }

    @Test
    public void testGetOrderedItemsByUserInput() {
        // When
        String userInput = "Coffee,Tea";
        // Action
        List<String> orderedItems = coffeeShopUserInputService.getOrderedItemsByUserInput(userInput);
        // Assert
        List<String> expectedOrderedItems = List.of("Coffee", "Tea");
        assertEquals(expectedOrderedItems, orderedItems);
    }
}