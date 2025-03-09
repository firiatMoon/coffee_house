package com.capybara.services.handlers;

import com.capybara.config.PassDataProperties;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.services.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.Optional;

import static com.capybara.constants.TelegramBotConstant.ALREADY_REGISTERED;
import static com.capybara.constants.TelegramBotConstant.NOT_RECOGNIZED;

@Slf4j
@Service
public class AfterRegistrationHandler {

    private final RestClient restClient;
    private final PassDataProperties passDataProperties;
    private final TelegramService telegramService;

    @Autowired
    public AfterRegistrationHandler(RestClient restClient, PassDataProperties passDataProperties, TelegramService telegramService) {
        this.restClient = restClient;
        this.passDataProperties = passDataProperties;
        this.telegramService = telegramService;
    }

    public SendMessage handle(String command, Long chatId) {
        return switch (command) {
            case "/start" -> startMessage(chatId);
            //получить бонус
            case "/bonus", "My bonus card" -> bonusCard(chatId);
            default -> defaultMessage(chatId);
        };
    }

    private SendMessage startMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(ALREADY_REGISTERED)
                .build();
    }

    private SendMessage bonusCard(Long chatId){
        log.info("Пришел запрос {}", chatId);
        Optional<TelegramBotInformation> telegramBotInformation = telegramService.getByChatId(chatId);
        if(telegramBotInformation.isPresent()){
            TelegramBotInformation telegramBotInformationInfo = telegramBotInformation.get();
            String amountBonusCard = null;
            if(telegramBotInformationInfo.getRegistrationState().equals(RegistrationState.REGISTERED)){
                amountBonusCard = getBonusCard(chatId);
                return SendMessage.builder()
                        .chatId(chatId)
                        .text(String.format("You have %s points", amountBonusCard))
                        .replyMarkup(ReplyKeyboardMarkup
                                .builder()
                                .resizeKeyboard(true)
                                .selective(true)
                                .keyboard(List.of(new KeyboardRow("My bonus card")))
                                .build())
                        .build();
            }
        }
        return defaultMessage(chatId);
    }

    private SendMessage defaultMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(NOT_RECOGNIZED)
                .build();
    }

    private String getBonusCard(Long chatId) {
        log.info("Sent request - bonus card {}", chatId );
        ResponseEntity<String> response = restClient.post()
                .uri(passDataProperties.getBonusCardUri())
                .contentType(MediaType.APPLICATION_JSON)
                .body(chatId)
                .retrieve()
                .toEntity(String.class);
        log.info("Received - {}", response.getBody());
        return response.getBody();
    }
}
