package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.capybara.coffee_house.constants.TelegramBotConstant.ASK_BIRTHDAY;

@Service
public class AskNameRegistrationHandler implements TelegramMessageHandler{
    private final ClientService clientService;

    @Autowired
    public AskNameRegistrationHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public SendMessage handle(String name, Long chatId, Client client) {
        client.setUsername(name);
        client.setRegistrationState(RegistrationState.ASK_BIRTHDATE);
        clientService.save(client);
        return SendMessage.builder()
                .chatId(chatId)
                .text(ASK_BIRTHDAY)
                .build();
    }
}
