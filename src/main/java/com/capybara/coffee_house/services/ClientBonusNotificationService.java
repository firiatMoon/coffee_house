package com.capybara.coffee_house.services;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.entities.ClientBonusCardTransaction;
import com.capybara.coffee_house.enums.BonusPointsAction;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.ClientRepository;
import com.capybara.coffee_house.services.telegrambot.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ClientBonusNotificationService {

    private final MessageSender messageSender;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientBonusNotificationService(MessageSender messageSender, ClientRepository clientRepository) {
        this.messageSender = messageSender;
        this.clientRepository = clientRepository;
    }

    public void notifyClient(ClientBonusCardTransaction transaction, BonusCard bonusCard) {
        Client client = clientRepository.findByPhone(transaction.getOrder().getClient().getPhone())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        String message = transaction.getOrder().getBonusPointsAction().equals(BonusPointsAction.USE)
                ? String.format("You have spent %s points. Your balance is %s",
                transaction.getPoints(), bonusCard.getAmount())
                : String.format("You have received %s points. Your balance is %s",
                transaction.getPoints(), bonusCard.getAmount());
        sendMessageClient(message, client.getChatId());
    }

    private void sendMessageClient(String message, Long chatId){
        SendMessage sendMessage = SendMessage.builder()
                .text(message)
                .chatId(chatId)
                .build();
        messageSender.sendMessage(sendMessage);
    }
}
