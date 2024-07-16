package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.capybara.coffee_house.constants.TelegramBotConstant.ASK_EMAIL;
import static com.capybara.coffee_house.constants.TelegramBotConstant.ASK_VALIDITY_BIRTHDAY;

@Service
public class AskBirthDateRegistrationHandler implements TelegramMessageHandler{
    private final ClientService clientService;

    @Autowired
    public AskBirthDateRegistrationHandler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public SendMessage handle(String birthday, Long chatId, Client client) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            client.setBirthday(birthDate);
            client.setRegistrationState(RegistrationState.ASK_EMAIL);
            clientService.save(client);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_EMAIL)
                    .build();
        } catch (DateTimeParseException exception){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_VALIDITY_BIRTHDAY)
                    .build();
        }
    }
}
