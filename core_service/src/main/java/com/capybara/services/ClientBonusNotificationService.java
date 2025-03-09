package com.capybara.services;

import com.capybara.dto.AnswerMessage;
import com.capybara.entities.BonusCard;
import com.capybara.entities.Client;
import com.capybara.entities.ClientBonusCardTransaction;
import com.capybara.enums.BonusPointsAction;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.rabbitmq.RabbitMQProducer;
import com.capybara.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientBonusNotificationService {

    private final ClientRepository clientRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public ClientBonusNotificationService(ClientRepository clientRepository, RabbitMQProducer rabbitMQProducer) {
        this.clientRepository = clientRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public void notifyClient(ClientBonusCardTransaction transaction, BonusCard bonusCard) {
        Client client = clientRepository.findByPhone(transaction.getOrder().getClient().getPhone())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        String message = transaction.getOrder().getBonusPointsAction().equals(BonusPointsAction.USE)
                ? String.format("You have spent %s points. Your balance is %s",
                transaction.getPoints(), bonusCard.getAmount())
                : String.format("You have received %s points. Your balance is %s",
                transaction.getPoints(), bonusCard.getAmount());
        AnswerMessage answerMessage = AnswerMessage.builder()
                .chatId(client.getChatId())
                .message(message)
                .build();
        bonusCardAfterTransactionMessage(answerMessage);
//        sendMessageClient(message, client.getChatId());
    }

    private void bonusCardAfterTransactionMessage(AnswerMessage answerMessage) {
        rabbitMQProducer.produceAnswerMessage(answerMessage);
    }

//    private void sendMessageClient(String message, Long chatId){
//        SendMessage sendMessage = SendMessage.builder()
//                .text(message)
//                .chatId(chatId)
//                .build();
//        messageSender.sendMessage(sendMessage);
//    }
}
