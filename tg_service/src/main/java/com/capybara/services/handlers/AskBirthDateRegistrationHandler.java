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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import static com.capybara.constants.TelegramBotConstant.ASK_PHONE;
import static com.capybara.constants.TelegramBotConstant.ASK_VALIDITY_BIRTHDAY;

@Slf4j
@Service
public class AskBirthDateRegistrationHandler implements TelegramMessageHandler{

    private final RestClient restClient;
    private final TelegramRepository telegramRepository;
    private final PassDataProperties passDataProperties;

    @Autowired
    public AskBirthDateRegistrationHandler(RestClient restClient, TelegramRepository telegramRepository,
                                           PassDataProperties passDataProperties) {
        this.restClient = restClient;
        this.telegramRepository = telegramRepository;
        this.passDataProperties = passDataProperties;
    }

    @Override
    public SendMessage handle(String birthday, Long chatId, TelegramBotInformation telegramBotInformation) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            passBirthday(chatId, birthDate);
            telegramBotInformation.setRegistrationState(RegistrationState.ASK_PHONE);
            telegramRepository.save(telegramBotInformation);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_PHONE)
                    .build();
        } catch (DateTimeParseException exception){
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_VALIDITY_BIRTHDAY)
                    .build();
        }
    }

    private void passBirthday(Long chatId, LocalDate birthday){
        Map<String, String> map = new HashMap<>();
        map.put("chatId", String.valueOf(chatId));
        map.put("birthday", birthday.toString());
        restClient.post()
                .uri(passDataProperties.getBirthdayUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(map)
                .retrieve()
                .toBodilessEntity();
    }
}
