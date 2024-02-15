package com.epam.service.coffeeshop;

import com.epam.exception.ProductNotFoundException;
import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.constants.coffeeshop.MenuItems.BACON_ROLL;
import static com.epam.constants.coffeeshop.MenuItems.EXTRA_MILK;
import static com.epam.constants.coffeeshop.MenuItems.FOAMED_MILK;
import static com.epam.constants.coffeeshop.MenuItems.LARGE_COFFEE;
import static com.epam.constants.coffeeshop.MenuItems.MEDIUM_COFFEE;
import static com.epam.constants.coffeeshop.MenuItems.ORANGE_JUICE;
import static com.epam.constants.coffeeshop.MenuItems.SMALL_COFFEE;
import static com.epam.constants.coffeeshop.MenuItems.SPECIAL_ROAST;
import static com.epam.model.OfferingType.BEVERAGE;
import static com.epam.model.OfferingType.SNACK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoffeeShopProductsServiceTest {
    private CoffeeShopProductsService service;

    @BeforeEach
    public void setUp() {
        service = new CoffeeShopProductsService();
    }

    @Test
    public void testGetProducts() {
        // When
        List<String> shoppingList = List.of(
                "Small Coffee with Extra Milk",
                "Medium coffee with special roast",
                "bacon roll",
                "large Coffee",
                "orange juice",
                "Large Coffee with Foamed milk");

        List<Product> expectedProducts = List.of(
                new Product(
                        new Offering(SMALL_COFFEE, new BigDecimal("2.55"), BEVERAGE),
                        new Extras(EXTRA_MILK, new BigDecimal("0.32"))),
                new Product(
                        new Offering(MEDIUM_COFFEE, new BigDecimal("3.05"), BEVERAGE),
                        new Extras(SPECIAL_ROAST, new BigDecimal("0.95"))),
                new Product(
                        new Offering(BACON_ROLL, new BigDecimal("4.53"), SNACK),
                        null),
                new Product(
                        new Offering(LARGE_COFFEE, new BigDecimal("3.55"), BEVERAGE),
                        null),
                new Product(
                        new Offering(ORANGE_JUICE, new BigDecimal("3.95"), BEVERAGE),
                        null),
                new Product(
                        new Offering(LARGE_COFFEE, new BigDecimal("3.55"), BEVERAGE),
                        new Extras(FOAMED_MILK, new BigDecimal("0.51")))
        );
        // Action
        List<Product> actualProducts = service.getProducts(shoppingList);
        // Assert
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetCoffeeAndJuiceAndOrangeJuiceProducts() {
        // When
        List<String> shoppingList = List.of(
                "coffee",
                "juice",
                "orange juice");

        List<Product> expectedProducts = List.of(
                new Product(
                        new Offering(MEDIUM_COFFEE, new BigDecimal("3.05"), BEVERAGE),
                        null),
                new Product(
                        new Offering(ORANGE_JUICE, new BigDecimal("3.95"), BEVERAGE),
                        null),
                new Product(
                        new Offering(ORANGE_JUICE, new BigDecimal("3.95"), BEVERAGE),
                        null));
        // Action
        List<Product> actualProducts = service.getProducts(shoppingList);
        // Assert
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testGetProductsOfferingNotFound() {
        List<String> shoppingList = List.of("Nonexistent product");
        assertThrows(ProductNotFoundException.class, () -> service.getProducts(shoppingList));
    }

    @Test
    public void testGetProductsExtrasNotFound() {
        List<String> shoppingList = List.of("Small coffee with Nonexistent extras");
        assertThrows(ProductNotFoundException.class, () -> service.getProducts(shoppingList));
    }
}