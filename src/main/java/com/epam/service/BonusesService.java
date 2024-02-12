package com.epam.service;

/**
 * The BonusesService interface provides a contract for managing user bonus points.
 * Implementations of this interface should define how bonus points are retrieved and saved for a user.
 */
public interface BonusesService {

    /**
     * Retrieves the current bonus points for the given user.
     *
     * @param userName the name of the user
     * @return the current bonus points for the user
     */
    int getBonusPoints(String userName);

    /**
     * Saves the given number of bonus points for the given user.
     *
     * @param userName     the name of the user
     * @param bonusesCount the number of bonus points to save for the user
     */
    void saveBonusPoints(String userName, int bonusesCount);
}