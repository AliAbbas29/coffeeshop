package com.epam.service.coffeeshop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoffeeShopBonusesServiceTest {
    private static final String USERNAME = "John";
    private CoffeeShopBonusesService service;
    private Path tempFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        service = new CoffeeShopBonusesService();
        tempFilePath = Files.createTempFile("testusers", ".txt");
        CoffeeShopBonusesService.FILE_NAME = tempFilePath.getFileName().toString();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFilePath);
    }

    @Test
    public void testGetBonusPoints() {
        // When
        Map<String, Integer> userBonuses = new HashMap<>();
        userBonuses.put(USERNAME, 5);
        CoffeeShopBonusesService.USER_BONUSES = userBonuses;
        // Action
        int bonusPoints = service.getBonusPoints(USERNAME);
        // Assert
        assertEquals(5, bonusPoints);
    }

    @Test
    public void testGetBonusPointsWithNullMap() {
        // When
        CoffeeShopBonusesService.USER_BONUSES = null;
        // Action
        int bonusPoints = service.getBonusPoints(USERNAME);
        // Assert
        assertEquals(0, bonusPoints);
    }

    @Test
    public void testSaveBonusPoints() {
        // When
        int bonusesCount = 10;
        // Action
        service.saveBonusPoints(USERNAME, bonusesCount);
        // Assert
        assertEquals(bonusesCount, CoffeeShopBonusesService.USER_BONUSES.get(USERNAME));
    }
}