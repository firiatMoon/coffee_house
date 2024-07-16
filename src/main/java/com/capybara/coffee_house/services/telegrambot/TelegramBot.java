package com.capybara.coffee_house.services.telegrambot;

import com.capybara.coffee_house.services.telegrambot.handlers.CallbackHandler;
import com.capybara.coffee_house.services.telegrambot.handlers.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final MessageSender messageSender;
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Autowired
    public TelegramBot(MessageSender messageSender, MessageHandler messageHandler, CallbackHandler callbackHandler) {
        this.messageSender = messageSender;
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void consume(Update update) {
        SendMessage message = null;
        if (update.hasMessage() && update.getMessage().hasText()) {
            message = messageHandler.handle(update);
        }
        if (update.hasCallbackQuery()) {
            message = callbackHandler.handleCallback(update);
        }

        if (message != null) {
            messageSender.sendMessage(message);
        }
    }
}







