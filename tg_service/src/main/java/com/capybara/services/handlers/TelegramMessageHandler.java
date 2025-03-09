package com.capybara.services.handlers;

import com.capybara.entities.TelegramBotInformation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TelegramMessageHandler {
    SendMessage handle(String pointRegistration, Long chatId, TelegramBotInformation telegramBotInformation);
}
