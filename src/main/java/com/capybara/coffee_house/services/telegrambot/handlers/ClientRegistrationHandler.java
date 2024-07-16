package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class ClientRegistrationHandler {
    private final Map<RegistrationState, TelegramMessageHandler> registrationHandlers = new HashMap<>();

    public ClientRegistrationHandler(RegistrationHandler registrationHandler,
                                     AskNameRegistrationHandler askNameRegistrationHandler,
                                     AskBirthDateRegistrationHandler askBirthDateRegistrationHandler,
                                     AskEmailRegistrationHandler askEmailRegistrationHandler) {
        registrationHandlers.put(RegistrationState.ASK_REGISTRATION, registrationHandler);
        registrationHandlers.put(RegistrationState.ASK_NAME, askNameRegistrationHandler);
        registrationHandlers.put(RegistrationState.ASK_BIRTHDATE, askBirthDateRegistrationHandler);
        registrationHandlers.put(RegistrationState.ASK_EMAIL, askEmailRegistrationHandler);
    }

    public SendMessage register(String message, Long chatId, Optional<Client> clientOptional){
        RegistrationState registrationState = clientOptional
                .map(Client::getRegistrationState)
                .orElse(RegistrationState.ASK_REGISTRATION);

        Client client = clientOptional.orElse(null);
        TelegramMessageHandler telegramMessageHandler = registrationHandlers.get(registrationState);
        return telegramMessageHandler.handle(message, chatId, client);
    }
}
