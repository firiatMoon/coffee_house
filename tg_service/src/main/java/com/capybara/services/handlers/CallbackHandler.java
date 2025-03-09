package com.capybara.services.handlers;


import com.capybara.config.PassDataProperties;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.repositories.TelegramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static com.capybara.constants.TelegramBotConstant.*;

@Slf4j
@Service
public class CallbackHandler {

    private final RestClient restClient;
    private final TelegramRepository telegramRepository;
    private final PassDataProperties passDataProperties;

    @Autowired
    public CallbackHandler(RestClient restClient, TelegramRepository telegramRepository, PassDataProperties passDataProperties) {
        this.restClient = restClient;
        this.telegramRepository = telegramRepository;
        this.passDataProperties = passDataProperties;
    }

    public SendMessage handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = null;
        if (update.getCallbackQuery().getData().equals(YES_BUTTON)) {
            sendChatId(chatId);
            TelegramBotInformation telegramBotInformation = TelegramBotInformation.builder()
                    .chatId(chatId)
                    .registrationState(RegistrationState.ASK_NAME)
                    .build();
            telegramRepository.save(telegramBotInformation);
            sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_NAME)
                    .build();
        } else if (update.getCallbackQuery().getData().equals(NO_BUTTON)) {
            sendMessage = SendMessage.builder()
                    .chatId(chatId)
                    .text(ANSWER_COME_BACK)
                    .build();
        }
        return sendMessage;
    }

    private void sendChatId(Long chatId) {
        Map<String, Long> map = new HashMap<>();
        map.put("chatId", chatId);
        restClient.post()
                .uri(passDataProperties.getCharIdUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toBodilessEntity();
    }

}
