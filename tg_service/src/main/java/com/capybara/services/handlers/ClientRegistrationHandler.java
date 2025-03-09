package com.capybara.services.handlers;

import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.capybara.constants.TelegramBotConstant.BONUSES_ARE_NOT_AVAILABLE_MESSAGE;

@Slf4j
@Service
public class  ClientRegistrationHandler {
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

    public SendMessage register(String message, Long chatId, Optional<TelegramBotInformation> telegramOptional){
        RegistrationState registrationState = telegramOptional
                .map(TelegramBotInformation::getRegistrationState)
                .orElse(RegistrationState.ASK_REGISTRATION);

        if (message.equals("/bonus")) {
            return bonusMessage(chatId);
        }
        TelegramBotInformation telegramBotInformationClient = telegramOptional.orElse(null);
        TelegramMessageHandler telegramMessageHandler = registrationHandlers.get(registrationState);
        return telegramMessageHandler.handle(message, chatId, telegramBotInformationClient);
    }

    private SendMessage bonusMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(BONUSES_ARE_NOT_AVAILABLE_MESSAGE)
                .build();
    }
}
