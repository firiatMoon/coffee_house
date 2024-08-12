package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.capybara.coffee_house.constants.TelegramBotConstant.*;

@Service
public class WaitingForRegistrationHandler implements TelegramMessageHandler{
    private final ClientService clientService;

    @Autowired
    public WaitingForRegistrationHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public SendMessage handle(String pointRegistration, Long chatId, Client client) {
        if(client.isActive()){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ALREADY_REGISTERED)
                    .build();
        } else if (client.getEmail() != null) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(CONFIRMATION_OF_REGISTRATION)
                    .build();
        }
        client.setRegistrationState(RegistrationState.ASK_EMAIL);
        clientService.save(client);
        return SendMessage.builder()
                .chatId(chatId)
                .text(ASK_EMAIL)
                .build();
    }

}
