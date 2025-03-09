package com.capybara.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
public class MessageSender {
    private final TelegramClient telegramClient;

    @Autowired
    public MessageSender(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void sendMessage(SendMessage sendMessage) {
        try{
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException exception) {
            log.error(exception.getMessage());
        }
    }
}
