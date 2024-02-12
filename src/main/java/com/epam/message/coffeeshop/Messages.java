package com.epam.message.coffeeshop;

public class Messages {
    public static final String WELCOME_MESSAGE = "Welcome to our coffee shop!";

    public static final String USER_MESSAGE = "Please enter your username:";

    public static final String ACTIONS = """
            Please type:
            'Menu' to check our menu,
            'Bonus' to check our bonus program,
            'Order' to make an order.""";

    public static final String INVALID_ACTION = "Please choose valid option!";

    public static final String HELP = "Please type 'Actions' to check available commands";

    public static final String MENU = """
            Menu:
            Offering
            - Coffee (small, medium, large): 2.55 CHF, 3.05 CHF, 3.55 CHF
            - Bacon Roll: 4.53 CHF
            - Freshly squeezed orange juice (0.25l): 3.95 CHF
            Extras
            - Extra milk 0.32 CHF
            - Foamed milk 0.51 CHF
            - Special roast coffee 0.95 CHF
            """;

    public static final String BONUS_PROGRAM = """
            Current bonus program includes:
                1) Every 5th drink is free!
                2) One of the extras is free for an order with beverage with snack!
            """;

    public static final String ORDER_START = """
            What would you like to order?
            Please choose any item from the menu with extras if needed.
            Example: large coffee with extra milk, small coffee with special roast, bacon roll, orange juice
            """;

    public static final String ORDER_FINISH = """
            Thank you for your order!
            Bon appetit!
            """;

    public static final String ORDER_NOT_AVAILABLE = """
            Please choose an available item.
            """;

    public static final String ORDER_BACK = "Please type 'Back' to return to the beginning";
}