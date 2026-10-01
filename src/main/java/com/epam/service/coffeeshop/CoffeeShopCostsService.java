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
import java.util.NoSuchElementException;
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
        this.bonusesService = Objects.requireNonNull(bonusesService, "BonusesService must not be null");
    }

    @Override
    public BigDecimal calculateTotalCosts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        boolean hasBeverage = isAnyProductPresentByOfferingType(products, BEVERAGE);
        boolean hasSnack = isAnyProductPresentByOfferingType(products, SNACK);
        boolean hasExtras = products.stream().anyMatch(product -> nonNull(product.extras()));

        if (hasBeverage) {
            processFreeFifthBeverageBonus(products);
            if (hasSnack && hasExtras) {
                applyFreeCheapestExtras(products);
            }
        }

        return products.stream()
                .map(Product::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isAnyProductPresentByOfferingType(List<Product> products, OfferingType type) {
        return products.stream()
                .filter(Objects::nonNull)
                .map(Product::offering)
                .filter(Objects::nonNull)
                .anyMatch(offering -> type.equals(offering.getType()));
    }

    private void processFreeFifthBeverageBonus(List<Product> products) {
        String userName = User.getCurrentUser();
        int currentBonusPoints = bonusesService.getBonusPoints(userName);
        int bonusCountAfterDiscount = calculateAndApplyFreeBeverages(products, currentBonusPoints);
        bonusesService.saveBonusPoints(userName, bonusCountAfterDiscount);
    }

    private int calculateAndApplyFreeBeverages(List<Product> products, int currentBonusPoints) {
        List<Offering> beverages = getBeverages(products);

        int actualBonus = currentBonusPoints;
        for (Offering beverage : beverages) {
            actualBonus++;
            if (actualBonus >= FREE_BEVERAGE_BONUS_MIN_AMOUNT) {
                beverage.setPrice(BigDecimal.ZERO);
                actualBonus = 0;
            }
        }
        return actualBonus;
    }

    private List<Offering> getBeverages(List<Product> products) {
        return products.stream()
                .filter(Objects::nonNull)
                .map(Product::offering)
                .filter(Objects::nonNull)
                .filter(offering -> BEVERAGE.equals(offering.getType()))
                .toList();
    }

    private void applyFreeCheapestExtras(List<Product> products) {
        Extras cheapestExtras = products.stream()
                .filter(Objects::nonNull)
                .map(Product::extras)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Extras::getPrice, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElseThrow(() -> new NoSuchElementException("No extras found to apply free discount"));

        cheapestExtras.setName(cheapestExtras.getName() + " (Free Extras)");
        cheapestExtras.setPrice(BigDecimal.ZERO);
    }
}