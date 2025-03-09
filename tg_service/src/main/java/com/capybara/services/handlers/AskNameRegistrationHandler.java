package com.capybara.services.handlers;


import com.capybara.config.PassDataProperties;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.repositories.TelegramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

import static com.capybara.constants.TelegramBotConstant.ASK_BIRTHDAY;

@Service
public class AskNameRegistrationHandler implements TelegramMessageHandler{

    private final RestClient restClient;
    private final TelegramRepository telegramRepository;
    private final PassDataProperties passDataProperties;

    @Autowired
    public AskNameRegistrationHandler(RestClient restClient, TelegramRepository telegramRepository, PassDataProperties passDataProperties) {
        this.restClient = restClient;
        this.telegramRepository = telegramRepository;
        this.passDataProperties = passDataProperties;
    }

    @Override
    public SendMessage handle(String name, Long chatId, TelegramBotInformation telegramBotInformation) {
        passUsername(chatId, name);
        telegramBotInformation.setRegistrationState(RegistrationState.ASK_BIRTHDATE);
        telegramRepository.save(telegramBotInformation);
        return SendMessage.builder()
                .chatId(chatId)
                .text(ASK_BIRTHDAY)
                .build();
    }

    private void passUsername(Long chatId, String username) {
        Map<String, String> map = new HashMap<>();
        map.put("chatId", chatId.toString());
        map.put("username", username);

        restClient.post()
                .uri(passDataProperties.getUsernameUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toBodilessEntity();
    }
}
