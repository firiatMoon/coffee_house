package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.repositories.ClientRepository;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.capybara.coffee_house.constants.TelegramBotConstant.*;

@Service
public class AskPhoneRegistrationHandler implements TelegramMessageHandler{
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private static final String PHONE_REGEX = "^([0-9]{10})$";

    @Autowired
    public AskPhoneRegistrationHandler(ClientService clientService, ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @Override
    public SendMessage handle(String phoneNumber, Long chatId, Client client) {
        if(isValidPhone(phoneNumber)){
            Optional<Client> clientOptional = clientRepository.findByPhone(phoneNumber);
            if(clientOptional.isEmpty()){
                client.setPhone(phoneNumber);
                client.setRegistrationState(RegistrationState.ASK_EMAIL);
                clientService.save(client);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(ASK_EMAIL)
                        .build();
            } else {
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(PHONE_ALREADY_EXISTS)
                        .build();
            }
        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_VALIDITY_PHONE)
                    .build();
        }
    }

    private boolean isValidPhone(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
