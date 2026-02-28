package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.services.BonusCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static com.capybara.coffee_house.constants.TelegramBotConstant.*;


@Service
public class AfterRegistrationHandler {
    private final BonusCardService bonusCardService;

    @Autowired
    public AfterRegistrationHandler(BonusCardService bonusCardService) {
        this.bonusCardService = bonusCardService;
    }

    public SendMessage handle(String command, Long chatId) {
        return switch (command) {
            case "/start" -> startMessage(chatId);
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
        BonusCard bonusCard = bonusCardService.getByChatId(chatId);
        return SendMessage.builder()
                .chatId(chatId)
                .text(String.format("You have %s points", bonusCard.getAmount()))
                .replyMarkup(ReplyKeyboardMarkup
                        .builder()
                        .resizeKeyboard(true)
                        .selective(true)
                        .keyboard(List.of(new KeyboardRow("My bonus card")))
                        .build())
                .build();
    }

    private SendMessage defaultMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(NOT_RECOGNIZED)
                .build();
    }
}
