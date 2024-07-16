package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.services.BonusCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Service
public class BonusCardHandler {
    private final BonusCardService bonusCardService;

    @Autowired
    public BonusCardHandler(BonusCardService bonusCardService) {
        this.bonusCardService = bonusCardService;
    }

    public SendMessage handle(Long clientId, Long chatId) {
        BonusCard bonusCard = bonusCardService.getByClientId(clientId);



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
}
