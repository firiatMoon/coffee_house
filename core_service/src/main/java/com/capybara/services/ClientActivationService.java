package com.capybara.services;

import com.capybara.dto.AnswerMessage;
import com.capybara.entities.Client;
import com.capybara.rabbitmq.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Base64;

import static com.capybara.constants.TelegramBotConstant.FINISH_REGISTRATION;


@Service
public class ClientActivationService {
    private final ClientService clientService;
    private final RabbitMQProducer rabbitMQProducer;


    @Autowired
    public ClientActivationService(ClientService clientService, RabbitMQProducer rabbitMQProducer) {
        this.clientService = clientService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public boolean activation(String encoderClientId) {
        byte[] decoderBytes = Base64.getDecoder().decode(encoderClientId);
        String clientId = new String(decoderBytes);
        if (clientId.isEmpty()) {
            return false;
        }
        Client client = clientService.getByChatId(Long.parseLong(clientId));
        if (client != null) {
            client.setActive(true);
            clientService.save(client);
            //send message to tg_service about finish registration
            AnswerMessage answerMessage = AnswerMessage.builder()
                    .chatId(client.getChatId())
                    .message(FINISH_REGISTRATION)
                    .build();
            registrationMessage(answerMessage);
            registrationState(client.getChatId());
            return true;
        }
        return false;
    }

    private void registrationMessage(AnswerMessage answerMessage) {
        rabbitMQProducer.produceAnswerMessage(answerMessage);
    }

    private void registrationState(Long chatId) {
        rabbitMQProducer.produceRegistrationState(chatId);
    }

}
