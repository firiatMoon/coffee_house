package com.capybara.services;

import com.capybara.services.handlers.CallbackHandler;
import com.capybara.services.handlers.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final MessageSender messageSender;
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Autowired
    public TelegramBot(MessageSender messageSender, MessageHandler messageHandler, CallbackHandler callbackHandler,
                       TelegramClient telegramClient) {
        this.messageSender = messageSender;
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;

        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "for registration"));
        botCommands.add(new BotCommand("/bonus", "show my points"));
        try {
            telegramClient.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: {}", e.getMessage());
        }
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







