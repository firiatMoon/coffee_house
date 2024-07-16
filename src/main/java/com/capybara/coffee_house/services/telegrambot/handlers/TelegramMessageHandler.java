package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramMessageHandler {
    SendMessage handle(String pointRegistration, Long chatId, Client client);
}
