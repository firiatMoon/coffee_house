package com.capybara.services.handlers;


import com.capybara.config.PassDataProperties;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.repositories.TelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.capybara.constants.TelegramBotConstant.*;

@Service
public class WaitingForRegistrationHandler implements TelegramMessageHandler{

    private final RestClient restClient;
    private final TelegramRepository telegramRepository;
    private final PassDataProperties passDataProperties;

    @Autowired
    public WaitingForRegistrationHandler(RestClient restClient, TelegramRepository telegramRepository, PassDataProperties passDataProperties) {
        this.restClient = restClient;
        this.telegramRepository = telegramRepository;
        this.passDataProperties = passDataProperties;
    }

    @Override
    public SendMessage handle(String pointRegistration, Long chatId, TelegramBotInformation telegramBotInformation) {
        if(getIsActive(chatId)) {
            telegramBotInformation.setRegistrationState(RegistrationState.ASK_EMAIL);
            telegramRepository.save(telegramBotInformation);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ALREADY_REGISTERED)
                    .build();
        } else if (!getIsActive(chatId)) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(CONFIRMATION_OF_REGISTRATION)
                    .build();
        }
        return SendMessage.builder()
                .chatId(chatId)
                .text(ASK_EMAIL)
                .build();
    }

    public boolean getIsActive(Long chatId){
        ResponseEntity<Boolean> response = restClient.post()
                .uri(passDataProperties.getIsActiveUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(chatId)
                .retrieve()
                .toEntity(Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }
}
