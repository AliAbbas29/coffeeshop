package com.epam.service.coffeeshop;

import com.epam.Generated;
import com.epam.service.UserInputService;

import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;

/**
 * The CoffeeShopUserInputService class provides an implementation of the UserInputService interface.
 * It handles user input in the coffee shop application.
 */

public class CoffeeShopUserInputService implements UserInputService {
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Reads a line of input from the user.
     *
     * @return a string that represents the user's input
     */
    @Generated
    public String getUserInput() {
        System.out.println();
        String userInput = handleUserInput();
        System.out.println();
        return userInput;
    }

    /**
     * Parses a string of user input into a list of ordered items.
     * The input string is expected to contain item names separated by commas.
     *
     * @param userInput a string of user input to parse
     * @return a list of strings that represent the ordered items
     */
    public List<String> getOrderedItemsByUserInput(String userInput) {
        return asList(userInput.split(","));
    }

    @Generated
    private String handleUserInput() {
        String userInput = "";
        while (userInput.isEmpty()) {
            userInput = SCANNER.nextLine();
            if (userInput.isEmpty()) {
                System.out.println("Please provide any input!");
            }
        }
        return userInput;
    }
}