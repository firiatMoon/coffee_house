package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.repositories.ClientRepository;
import com.capybara.coffee_house.services.ClientService;
import com.capybara.coffee_house.services.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.capybara.coffee_house.constants.TelegramBotConstant.*;
@Slf4j
@Service
public class AskEmailRegistrationHandler implements TelegramMessageHandler{
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final EmailSenderService emailSenderService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Autowired
    public AskEmailRegistrationHandler(ClientService clientService, ClientRepository clientRepository, EmailSenderService emailSenderService) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public SendMessage handle(String email, Long chatId, Client client) {
        if (isValidEmail(email)) {
            Optional<Client> clientOptional = clientRepository.findByEmailIgnoreCase(email);
            if (clientOptional.isEmpty()) {
                client.setEmail(email);
                client.setRegistrationState(RegistrationState.WAITING_FOR_REGISTRATION);
                clientService.save(client);
                emailSenderService.send(email, encode(chatId));
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(CONFIRMATION_OF_REGISTRATION)
                        .build();
            } else {
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(EMAIL_ALREADY_EXISTS)
                        .build();
            }
        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_VALIDITY_EMAIL)
                    .build();
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String encode(Long chatId) {
        byte[] encodedBytes = Base64.getEncoder().encode(String.valueOf(chatId).getBytes());
        return new String(encodedBytes);
    }
}
