package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class MessageHandler {

    private final ClientService clientService;
    private final ClientRegistrationHandler clientRegistrationHandler;
    private final AfterRegistrationHandler afterRegistrationHandler;

    @Autowired
    public MessageHandler(ClientService clientService, ClientRegistrationHandler clientRegistrationHandler,
                          AfterRegistrationHandler afterRegistrationHandler) {
        this.clientService = clientService;
        this.clientRegistrationHandler = clientRegistrationHandler;
        this.afterRegistrationHandler = afterRegistrationHandler;
    }

    public SendMessage handle(Update update) {
        String messageText = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        Optional<Client> clientOptional = clientService.getByChatId(chatId);
        if (isClientRegistered(clientOptional)) {
            return afterRegistrationHandler.handle(messageText, chatId);
        } else {
            return clientRegistrationHandler.register(messageText, chatId, clientOptional);
        }
    }

    private boolean isClientRegistered(Optional<Client> clientOptional) {
        return clientOptional
                .map(client -> client.getRegistrationState().equals(RegistrationState.REGISTERED))
                .orElse(false);
    }
}
