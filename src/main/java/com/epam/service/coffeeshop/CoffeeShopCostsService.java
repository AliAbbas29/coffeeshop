package com.epam.service.coffeeshop;

import com.epam.model.Extras;
import com.epam.model.Offering;
import com.epam.model.OfferingType;
import com.epam.model.Product;
import com.epam.model.User;
import com.epam.service.BonusesService;
import com.epam.service.CostsService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.epam.model.OfferingType.BEVERAGE;
import static com.epam.model.OfferingType.SNACK;
import static com.epam.service.coffeeshop.CoffeeShopBonusesService.FREE_BEVERAGE_BONUS_MIN_AMOUNT;
import static java.util.Objects.nonNull;

/**
 * The CoffeeShopCostsService class provides an implementation of the CostsService interface.
 * It calculates the total cost of a list of products, applying discounts and special offers as necessary.
 * It also interacts with the CoffeeShopBonusesService to apply and update user bonus points.
 */
public class CoffeeShopCostsService implements CostsService {
    private final BonusesService bonusesService;

    public CoffeeShopCostsService(BonusesService bonusesService) {
        this.bonusesService = bonusesService;
    }

    /**
     * Calculates the total cost of the given list of products.
     * The method applies discounts and special offers as necessary, and updates user bonus points.
     *
     * @param products a list of products to calculate the total cost for
     * @return the total cost of the products
     */
    public BigDecimal calculateTotalCosts(List<Product> products) {
        boolean anyBeveragePresent = isAnyProductPresentByOfferingType(products, BEVERAGE);
        boolean anySnackPresent = isAnyProductPresentByOfferingType(products, SNACK);
        boolean anyExtrasPresent = products.stream().anyMatch(product -> nonNull(product.extras()));

        if (anyBeveragePresent) {
            processFreeFifthBeverageBonus(products);
            if (anySnackPresent && anyExtrasPresent) {
                makeFreeCheapestExtras(products);
            }
        }
        return products.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isAnyProductPresentByOfferingType(List<Product> products, OfferingType type) {
        return products.stream().anyMatch(product -> type == product.offering().getType());
    }

    private void processFreeFifthBeverageBonus(List<Product> products) {
        String userName = User.getCurrentUser();
        int currentBonusPoints = bonusesService.getBonusPoints(userName);
        int bonusCountAfterDiscount = makeFreeEveryFifthBeverage(products, currentBonusPoints);
        bonusesService.saveBonusPoints(userName, bonusCountAfterDiscount);
    }

    private int makeFreeEveryFifthBeverage(List<Product> products, int currentBonusPoints) {
        List<Offering> beverages = getBeverages(products);

        int actualBonus = currentBonusPoints;
        for (Offering beverage : beverages) {
            actualBonus = actualBonus + 1;
            if (actualBonus == FREE_BEVERAGE_BONUS_MIN_AMOUNT) {
                beverage.setPrice(BigDecimal.ZERO);
                actualBonus = 0;
            }
        }
        return actualBonus;
    }

    private List<Offering> getBeverages(List<Product> products) {
        return products.stream()
                .map(Product::offering)
                .filter(offering -> BEVERAGE == offering.getType()).toList();
    }

    private void makeFreeCheapestExtras(List<Product> products) {
        Extras cheapestExtras = products.stream()
                .map(Product::extras)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Extras::getPrice)).get();
        cheapestExtras.setName(cheapestExtras.getName() + " (Free Extras)");
        cheapestExtras.setPrice(BigDecimal.ZERO);
    }
}