package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;

public interface TelegramMessageHandler {
    void handle(String message, Long chatId, Client client);
}
