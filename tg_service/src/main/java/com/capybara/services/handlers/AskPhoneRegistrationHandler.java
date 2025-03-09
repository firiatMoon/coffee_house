package com.capybara.services.handlers;

import com.capybara.config.PassDataProperties;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.repositories.TelegramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.capybara.constants.TelegramBotConstant.*;

@Slf4j
@Service
public class AskPhoneRegistrationHandler implements TelegramMessageHandler{
    private static final String PHONE_REGEX = "^([0-9]{10})$";

    private final RestClient restClient;
    private final TelegramRepository telegramRepository;
    private final PassDataProperties passDataProperties;

    @Autowired
    public AskPhoneRegistrationHandler(RestClient restClient, TelegramRepository telegramRepository, PassDataProperties passDataProperties) {
        this.restClient = restClient;
        this.telegramRepository = telegramRepository;
        this.passDataProperties = passDataProperties;
    }

    @Override
    public SendMessage handle(String phoneNumber, Long chatId, TelegramBotInformation telegramBotInformation) {
        if(isValidPhone(phoneNumber)){
            //отправить телефон и chat id, там проверить
            if(!passPhoneNumber(chatId, phoneNumber)){
                telegramBotInformation.setRegistrationState(RegistrationState.ASK_EMAIL);
                telegramRepository.save(telegramBotInformation);
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

    private boolean passPhoneNumber(Long chatId, String phoneNumber) {
        Map<String, String> map = new HashMap<>();
        map.put("chatId", chatId.toString());
        map.put("phoneNumber", phoneNumber);
        ResponseEntity<Boolean> response = restClient.post()
                .uri(passDataProperties.getPhoneUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toEntity(Boolean.class);

        return Boolean.TRUE.equals(response.getBody());
    }
}
