package com.capybara.coffee_house.services;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import com.capybara.coffee_house.services.telegrambot.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Base64;
import java.util.Optional;

import static com.capybara.coffee_house.constants.TelegramBotConstant.FINISH_REGISTRATION;

@Service
public class ClientActivationService {
    private final ClientService clientService;
    private final MessageSender messageSender;

    @Autowired
    public ClientActivationService(ClientService clientService, MessageSender messageSender) {
        this.clientService = clientService;
        this.messageSender = messageSender;
    }

    public boolean activation(String encoderClientId) {
        byte[] decoderBytes = Base64.getDecoder().decode(encoderClientId);
        String clientId = new String(decoderBytes);
        if (clientId.isEmpty()) {
            return false;
        }
        Optional<Client> clientOptional = clientService.getByChatId(Long.parseLong(clientId));
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setActive(true);
            client.setRegistrationState(RegistrationState.REGISTERED);
            clientService.save(client);
            registrationMessage(clientId);
            return true;
        }
        return false;
    }

    private void registrationMessage(String clientId) {
        SendMessage message = SendMessage.builder()
                .chatId(clientId)
                .text(FINISH_REGISTRATION)
                .build();
        messageSender.sendMessage(message);
    }

}
