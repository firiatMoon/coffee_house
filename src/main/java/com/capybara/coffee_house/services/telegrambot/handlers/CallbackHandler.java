package com.capybara.coffee_house.services.telegrambot.handlers;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.BonusCardService;
import com.capybara.coffee_house.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static com.capybara.coffee_house.constants.TelegramBotConstant.*;

@Service
public class CallbackHandler {
    private final ClientService clientService;
    private final BonusCardService bonusCardService;

    @Autowired
    public CallbackHandler(ClientService clientService, BonusCardService bonusCardService) {
        this.clientService = clientService;
        this.bonusCardService = bonusCardService;
    }

    public SendMessage handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage sendMessage = null;
        if (update.getCallbackQuery().getData().equals(YES_BUTTON)) {
            Client client = Client.builder()
                    .chatId(chatId)
                    .registrationState(RegistrationState.ASK_NAME)
                    .build();
            clientService.createClient(client);
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

    private AnswerCallbackQuery callbackAnswer(Update update){
        CallbackQuery callbackQuery = update.getCallbackQuery();
        return AnswerCallbackQuery.builder()
                .text(ANSWER_COME_BACK)
                .callbackQueryId(callbackQuery.getId())
                .showAlert(true)
                .build();
    }

}
