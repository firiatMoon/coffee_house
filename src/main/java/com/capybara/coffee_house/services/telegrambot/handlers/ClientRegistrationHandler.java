package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.capybara.coffee_house.constants.TelegramBotConstant.BONUSES_ARE_NOT_AVAILABLE_MESSAGE;

@Slf4j
@Service
public class ClientRegistrationHandler {
    private final Map<RegistrationState, TelegramMessageHandler> registrationHandlers = new HashMap<>();

    public ClientRegistrationHandler(RegistrationHandler registrationHandler,
                                     AskNameRegistrationHandler askNameRegistrationHandler,
                                     AskBirthDateRegistrationHandler askBirthDateRegistrationHandler,
                                     AskPhoneRegistrationHandler askPhoneRegistrationHandler,
                                     AskEmailRegistrationHandler askEmailRegistrationHandler, WaitingForRegistrationHandler waitingForRegistrationHandler) {
        registrationHandlers.put(RegistrationState.ASK_REGISTRATION, registrationHandler);
        registrationHandlers.put(RegistrationState.ASK_NAME, askNameRegistrationHandler);
        registrationHandlers.put(RegistrationState.ASK_BIRTHDATE, askBirthDateRegistrationHandler);
        registrationHandlers.put(RegistrationState.ASK_PHONE, askPhoneRegistrationHandler);
        registrationHandlers.put(RegistrationState.ASK_EMAIL, askEmailRegistrationHandler);
        registrationHandlers.put(RegistrationState.WAITING_FOR_REGISTRATION, waitingForRegistrationHandler);
    }

    public SendMessage register(String message, Long chatId, Optional<Client> clientOptional){
        RegistrationState registrationState = clientOptional
                .map(Client::getRegistrationState)
                .orElse(RegistrationState.ASK_REGISTRATION);

        if (message.equals("/bonus")) {
            return bonusMessage(chatId);
        }

        Client client = clientOptional.orElse(null);
        TelegramMessageHandler telegramMessageHandler = registrationHandlers.get(registrationState);
        return telegramMessageHandler.handle(message, chatId, client);
    }

    private SendMessage bonusMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(BONUSES_ARE_NOT_AVAILABLE_MESSAGE)
                .build();
    }
}
