package com.epam.service.coffeeshop;

import com.epam.exception.ProductNotFoundException;
import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

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
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        new Extras("Extra milk", new BigDecimal("0.32"))),
                new Product(
                        new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE),
                        new Extras("Special roast", new BigDecimal("0.95"))),
                new Product(
                        new Offering("Bacon roll", new BigDecimal("4.53"), SNACK),
                        null),
                new Product(
                        new Offering("Large coffee", new BigDecimal("3.55"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Freshly squeezed orange juice (0.25l)", new BigDecimal("3.95"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Large coffee", new BigDecimal("3.55"), BEVERAGE),
                        new Extras("Foamed milk", new BigDecimal("0.51")))
        );
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