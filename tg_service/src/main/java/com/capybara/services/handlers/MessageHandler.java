package com.capybara.services.handlers;


import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.services.TelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class MessageHandler {
    private final TelegramService telegramService;
    private final ClientRegistrationHandler clientRegistrationHandler;
    private final AfterRegistrationHandler afterRegistrationHandler;

    @Autowired
    public MessageHandler(TelegramService telegramService, ClientRegistrationHandler clientRegistrationHandler,
                          AfterRegistrationHandler afterRegistrationHandler) {
        this.telegramService = telegramService;
        this.clientRegistrationHandler = clientRegistrationHandler;
        this.afterRegistrationHandler = afterRegistrationHandler;
    }

    public SendMessage handle(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Optional<TelegramBotInformation> telegramOptional = telegramService.getByChatId(chatId);
        if (isClientRegistered(telegramOptional)) {
            return afterRegistrationHandler.handle(messageText, chatId);
        } else {
            return clientRegistrationHandler.register(messageText, chatId, telegramOptional);
        }
    }

    private boolean isClientRegistered(Optional<TelegramBotInformation> telegramOptional) {
        return telegramOptional
                .map(client -> client.getRegistrationState().equals(RegistrationState.REGISTERED))
                .orElse(false);
    }
}
