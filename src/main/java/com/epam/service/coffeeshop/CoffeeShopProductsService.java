package com.epam.service.coffeeshop;

import com.epam.exception.ProductNotFoundException;
import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.Product;
import com.epam.service.ProductsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.epam.model.OfferingType.BEVERAGE;
import static com.epam.model.OfferingType.SNACK;
import static java.util.Objects.nonNull;

/**
 * Products service related to Coffee Shop.
 * Maintains a list of available offerings and extras, and can generate a list of Product objects based on a shopping list.
 */
public class CoffeeShopProductsService implements ProductsService {
    private final List<Offering> availableOfferings = new ArrayList<>();
    private final List<Extras> availableExtras = new ArrayList<>();

    {
        availableOfferings.add(new Offering("Small coffee", new BigDecimal("2.55"), BEVERAGE));
        availableOfferings.add(new Offering("Medium coffee", new BigDecimal("3.05"), BEVERAGE));
        availableOfferings.add(new Offering("Large coffee", new BigDecimal("3.55"), BEVERAGE));
        availableOfferings.add(new Offering("Bacon roll", new BigDecimal("4.53"), SNACK));
        availableOfferings.add(new Offering("Freshly squeezed orange juice (0.25l)", new BigDecimal("3.95"), BEVERAGE));

        availableExtras.add(new Extras("Extra milk", new BigDecimal("0.32")));
        availableExtras.add(new Extras("Foamed milk", new BigDecimal("0.51")));
        availableExtras.add(new Extras("Special roast", new BigDecimal("0.95")));
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
        for (Offering offering : this.availableOfferings) {
            if (offering.getName().toLowerCase().contains(productName.trim().toLowerCase())) {
                return new Offering(offering);
            }
        }
        throw new ProductNotFoundException(productName);
    }

    private Extras getExtras(String extrasName) {
        for (Extras extras : this.availableExtras) {
            if (extras.getName().toLowerCase().contains(extrasName.trim().toLowerCase())) {
                return new Extras(extras);
            }
        }
        throw new ProductNotFoundException(extrasName);
    }
}