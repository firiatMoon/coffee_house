package com.capybara.coffee_house.services;

import com.capybara.coffee_house.services.telegrambot.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ClientBonusNotificationService {

    private final MessageSender messageSender;

    @Autowired
    public ClientBonusNotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void notifyClient(String message, Long chatId){
        SendMessage sendMessage = SendMessage.builder()
                .text(message)
                .chatId(chatId)
                .build();
        messageSender.sendMessage(sendMessage);
    }
}
