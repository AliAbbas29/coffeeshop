package com.epam.service.coffeeshop;

import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import com.epam.service.BonusesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.model.OfferingType.BEVERAGE;
import static com.epam.model.OfferingType.SNACK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CoffeeShopCostsServiceTest {
    private CoffeeShopCostsService service;

    @BeforeEach
    public void setUp() {
        BonusesService bonusesService = mock(BonusesService.class);
        service = new CoffeeShopCostsService(bonusesService);
    }

    @Test
    public void testGenerateTotalCostReceiptForBeveragesWithoutSnack() {
        // When
        List<Product> productList = List.of(
                new Product(
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        new Extras("Extra milk", new BigDecimal("0.32"))),
                new Product(
                        new Offering("Freshly squeezed orange juice (0.25l)", new BigDecimal("3.95"), BEVERAGE),
                        null));
        // Action
        BigDecimal actualCosts = service.calculateTotalCosts(productList);
        // Assert
        assertEquals(new BigDecimal("6.82"), actualCosts);
    }

    @Test
    public void testGenerateTotalCostReceiptForSnackWithoutBeverages() {
        // When
        List<Product> productList = List.of(
                new Product(
                        new Offering("Bacon roll", new BigDecimal("4.53"), SNACK),
                        null));
        // Action
        BigDecimal actualCosts = service.calculateTotalCosts(productList);
        // Assert
        assertEquals(new BigDecimal("4.53"), actualCosts);
    }

    @Test
    public void testGenerateTotalCostReceiptForBeveragesAndSnack() {
        // When
        List<Product> productList = List.of(
                new Product(
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Bacon roll", new BigDecimal("4.53"), SNACK),
                        null));
        // Action
        BigDecimal actualCosts = service.calculateTotalCosts(productList);
        // Assert
        assertEquals(new BigDecimal("7.08"), actualCosts);
    }

    @Test
    public void testGenerateTotalCostReceiptForBeveragesWithExtrasAndSnack() {
        // When
        List<Product> productList = List.of(
                new Product(
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        new Extras("Extra milk", new BigDecimal("0.32"))),
                new Product(
                        new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE),
                        new Extras("Special roast", new BigDecimal("0.95"))),
                new Product(
                        new Offering("Bacon roll", new BigDecimal("4.53"), SNACK),
                        null));
        // Action
        BigDecimal actualCosts = service.calculateTotalCosts(productList);
        // Assert
        assertEquals(new BigDecimal("11.08"), actualCosts);
    }

    @Test
    public void testGenerateTotalCostReceiptWithFreeEvery5thDrink() {
        // When
        List<Product> productList = List.of(
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
                        null),
                new Product(
                        new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Freshly squeezed orange juice (0.25l)", new BigDecimal("3.95"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Large coffee", new BigDecimal("3.55"), BEVERAGE),
                        null),
                new Product(
                        new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE),
                        null)
        );
        // Action
        BigDecimal actualCosts = service.calculateTotalCosts(productList);
        // Assert
        assertEquals(new BigDecimal("31.68"), actualCosts);
    }
}