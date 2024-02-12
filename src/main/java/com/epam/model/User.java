package com.epam.model;

public class User {
    private static String CURRENT_USER;

    public static String getCurrentUser() {
        return CURRENT_USER;
    }

    public static void setCurrentUser(String userName) {
        CURRENT_USER = userName;
    }
}
