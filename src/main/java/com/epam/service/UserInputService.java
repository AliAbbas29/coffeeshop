package com.epam.service;

import java.util.List;

/**
 * The UserInputService interface provides a contract for handling user input.
 * Implementations of this interface should define how user input is received and processed.
 */
public interface UserInputService {

    /**
     * Reads a line of input from the user.
     *
     * @return a string that represents the user's input
     */
    String getUserInput();

    /**
     * Parses a string of user input into a list of ordered items.
     * The input string is expected to contain item names separated by some delimiter.
     *
     * @param userInput a string of user input to parse
     * @return a list of strings that represent the ordered items
     */
    List<String> getOrderedItemsByUserInput(String userInput);
}