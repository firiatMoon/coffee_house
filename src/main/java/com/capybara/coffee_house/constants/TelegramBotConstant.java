package com.capybara.coffee_house.constants;

import org.springframework.stereotype.Component;

@Component
public class TelegramBotConstant {
    public static final String ASK_REGISTRATION = "Do you want to register?";
    public static final String YES_BUTTON = "YES_BUTTON";
    public static final String NO_BUTTON = "NO_BUTTON";
    public static final String ASK_NAME = "Let's introduce ourselves, I'm a coffee bot, what's your name?";
    public static final String ASK_BIRTHDAY = "Nice to meet you! Please enter your date of birth in the format DD.MM.YYYY";
    public static final String ASK_VALIDITY_BIRTHDAY = "Please enter a valid your date of birth in the format DD.MM.YYYY";
    public static final String ASK_EMAIL = "Please enter your email address";
    public static final String ASK_VALIDITY_EMAIL = "Please enter a valid email address";
    public static final String EMAIL_ALREADY_EXISTS ="This email address is already in use. Please enter a different email address";
    public static final String CONFIRMATION_OF_REGISTRATION ="An email has been sent to your email. " +
            "Follow the link in the email to confirm registration";
    public static final String FINISH_REGISTRATION = "Thank you for registering";
    public static final String ALREADY_REGISTERED = "You are already registered";
    public static final String ANSWER_COME_BACK = "Come back when you're ready";
    public static final String NOT_RECOGNIZED = "Sorry command was not recognized";
    public static final String START_MESSAGE = "Welcome to the coffee shop! This is your bonus card." +
                                               "If you want to know how many points you have, just click on it:";
}
