package com.epam.service.coffeeshop;

import com.epam.exception.ProductNotFoundException;
import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import com.epam.service.ProductsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import static java.util.Objects.nonNull;

/**
 * Products service related to Coffee Shop.
 * Maintains maps of available offerings and extras, and can generate a list of Product objects based on a shopping list.
 */
public class CoffeeShopProductsService implements ProductsService {
    private final Map<String, Offering> availableOfferings = new HashMap<>();
    private final Map<String, Extras> availableExtras = new HashMap<>();

    {
        availableOfferings.put(SMALL_COFFEE, new Offering(SMALL_COFFEE, new BigDecimal("2.55"), BEVERAGE));
        availableOfferings.put(MEDIUM_COFFEE, new Offering(MEDIUM_COFFEE, new BigDecimal("3.05"), BEVERAGE));
        availableOfferings.put(LARGE_COFFEE, new Offering(LARGE_COFFEE, new BigDecimal("3.55"), BEVERAGE));
        availableOfferings.put(BACON_ROLL, new Offering(BACON_ROLL, new BigDecimal("4.53"), SNACK));
        availableOfferings.put(ORANGE_JUICE, new Offering(ORANGE_JUICE, new BigDecimal("3.95"), BEVERAGE));

        availableExtras.put(EXTRA_MILK, new Extras(EXTRA_MILK, new BigDecimal("0.32")));
        availableExtras.put(FOAMED_MILK, new Extras(FOAMED_MILK, new BigDecimal("0.51")));
        availableExtras.put(SPECIAL_ROAST, new Extras(SPECIAL_ROAST, new BigDecimal("0.95")));
    }

    /**
     * Generates a list of Product objects based on the provided shopping list.
     * Each string in the shopping list should correspond to a unique product.
     *
     * @param shoppingList a list of product names that the customer wants to purchase
     * @return a list of Product objects that correspond to the items in the shopping list
     */
    public List<Product> getProducts(List<String> shoppingList) {
        List<Product> products = new ArrayList<>();
        for (String item : shoppingList) {
            String[] parts = item.split(" with ");
            String offering = parts[0];
            String extras = parts.length > 1 ? parts[1] : null;

            products.add(new Product(
                    getOffering(offering),
                    nonNull(extras) ? getExtras(extras) : null));
        }
        return products;
    }

    private Offering getOffering(String productName) {
        String productToFind = productName.trim();
        for (Offering offering : this.availableOfferings.values()) {
            if (("coffee").equalsIgnoreCase(productToFind)) {
                return availableOfferings.get(MEDIUM_COFFEE);
            } else if ("juice".equalsIgnoreCase(productToFind) || "orange juice".equalsIgnoreCase(productToFind)) {
                return availableOfferings.get(ORANGE_JUICE);
            } else if (offering.getName().equalsIgnoreCase(productToFind)) {
                return availableOfferings.get(offering.getName());
            }
        }
        throw new ProductNotFoundException(productToFind);
    }

    private Extras getExtras(String extrasName) {
        for (Extras extras : this.availableExtras.values()) {
            if (extras.getName().equalsIgnoreCase(extrasName.trim())) {
                return availableExtras.get(extras.getName());
            }
        }
        throw new ProductNotFoundException(extrasName);
    }
}