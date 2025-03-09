package com.capybara.services.handlers;


import com.capybara.entities.TelegramBotInformation;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import static com.capybara.constants.TelegramBotConstant.*;


@Service
public class RegistrationHandler implements TelegramMessageHandler {


    @Override
    public SendMessage handle(String message, Long chatId, TelegramBotInformation telegramBotInformation) {
        return SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(ASK_REGISTRATION)
                .replyMarkup(InlineKeyboardMarkup.builder()
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                .text("Yes")
                                .callbackData(YES_BUTTON)
                                .build()))
                        .keyboardRow(new InlineKeyboardRow(InlineKeyboardButton.builder()
                                .text("No")
                                .callbackData(NO_BUTTON)
                                .build()))
                        .build())
                .build();
    }
}
