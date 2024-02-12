package com.epam.service.coffeeshop;

import com.epam.service.BonusesService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.isNull;

/**
 * The CoffeeShopBonusesService class provides an implementation of the BonusesService interface.
 * It manages user bonus points, including loading and saving bonus points to a file.
 */
public class CoffeeShopBonusesService implements BonusesService {
    public static final int FREE_BEVERAGE_BONUS_MIN_AMOUNT = 5;
    static Map<String, Integer> USER_BONUSES;
    static String FILE_NAME = "users.txt";

    /**
     * Retrieves the current bonus points for the given user.
     *
     * @param userName the name of the user
     * @return the current bonus points for the user
     */
    public int getBonusPoints(String userName) {
        if (isNull(USER_BONUSES)) {
            loadUserBonuses();
        }
        return USER_BONUSES.getOrDefault(userName, 0);
    }

    /**
     * Saves the given number of bonus points for the given user.
     *
     * @param userName     the name of the user
     * @param bonusesCount the number of bonus points to save for the user
     */
    public void saveBonusPoints(String userName, int bonusesCount) {
        USER_BONUSES.put(userName, bonusesCount);
        saveFile();
    }

    private void loadUserBonuses() {
        USER_BONUSES = new HashMap<>();
        fillUserBonusesMapFromFile();
    }

    private void fillUserBonusesMapFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split(" ");
                    USER_BONUSES.put(line[0], Integer.parseInt(line[1]));
                }
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while loading user bonuses.");
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Map.Entry<String, Integer> entry : USER_BONUSES.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving user bonuses.");
            e.printStackTrace();
        }
    }
}
