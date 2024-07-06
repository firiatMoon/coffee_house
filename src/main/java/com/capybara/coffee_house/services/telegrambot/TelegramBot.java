package com.capybara.coffee_house.services.telegrambot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
public class TelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private static final String ASK_REGISTRATION = "Do you want to register?";
    private static final String YES_BUTTON = "YES_BUTTON";
    private static final String NO_BUTTON = "NO_BUTTON";

    @Autowired
    public TelegramBot(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {
                register(chatId);
            }
        }
    }

    private void register(long chatId) {
        SendMessage sendMessage = SendMessage
                .builder()
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
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException exception) {
            log.error(exception.getMessage());
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException exception){
            log.error(exception.getMessage());
        }
    }
}





