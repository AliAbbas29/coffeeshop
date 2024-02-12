package com.epam.service.coffeeshop;

import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.epam.model.OfferingType.BEVERAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeShopReceiptServiceTest {
    private CoffeeShopReceiptService coffeeShopReceiptService;

    @BeforeEach
    public void setUp() {
        coffeeShopReceiptService = new CoffeeShopReceiptService();
    }

    @Test
    public void testGenerateReceipt() {
        // When
        List<Product> products = List.of(
                new Product(
                        new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE),
                        new Extras("Extra milk", new BigDecimal("0.32"))),
                new Product(
                        new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE),
                        new Extras("Special roast", new BigDecimal("0.95"))));
        BigDecimal totalCost = new BigDecimal("6.87");

        // Action
        String actualReceipt = coffeeShopReceiptService.generateReceipt(products, totalCost);

        // Assert
        String expectedReceipt = """
                ===== Receipt =====
                Small coffee with Extra milk: 2.87 CHF
                Medium coffee with Special roast: 4.00 CHF
                -------------------
                Total Cost: 6.87 CHF
                ===================""";
        assertEquals(expectedReceipt, actualReceipt);
    }
}