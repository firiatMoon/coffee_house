package com.capybara.services;


import com.capybara.dto.AnswerMessage;
import com.capybara.dto.ClientDto;
import com.capybara.dto.MailParams;
import com.capybara.entities.BonusCard;
import com.capybara.entities.Client;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.rabbitmq.RabbitMQProducer;
import com.capybara.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.capybara.constants.TelegramBotConstant.EMAIL_ALREADY_EXISTS;

@Slf4j
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BonusCardService bonusCardService;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public ClientService(ClientRepository clientRepository, BonusCardService bonusCardService, RabbitMQProducer rabbitMQProducer) {
        this.clientRepository = clientRepository;
        this.bonusCardService = bonusCardService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    public Client getByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    //create Client
    @Transactional
    public void createClient(Long chatId) {
        Client client = Client.builder()
                .chatId(chatId)
                .build();
        Client savedClient = clientRepository.save(client);
        BonusCard bonusCard = BonusCard.builder()
                .client(savedClient)
                .amount(BigDecimal.valueOf(0.0))
                .discountPercent(5)
                .build();

        bonusCardService.save(bonusCard);
    }

    public void saveUsername(Map<String, String> map) {
        Client client = clientRepository.findByChatId(Long.parseLong(map.get("chatId")));
        if(client != null) {
            client.setUsername(map.get("username"));
            clientRepository.save(client);
        } else {
            throw new EntityNotFoundException("Client not found");
        }
    }

    public void saveBirthday(Map<String, String> map) {
        Client client = clientRepository.findByChatId(Long.parseLong(map.get("chatId")));
        client.setBirthday(LocalDate.parse(map.get("birthday")));
        clientRepository.save(client);
    }

    public boolean savePhoneNumber(Map<String, String> map) {
        Optional<Client> clientOptional = clientRepository.findByPhone(map.get("phoneNumber"));
        if(clientOptional.isEmpty()) {
            Client client = clientRepository.findByChatId(Long.parseLong(map.get("chatId")));
            client.setPhone(map.get("phoneNumber"));
            clientRepository.save(client);
            return false;
        }
        return true;
    }

    public void registrationMail(MailParams mailParams){
        Optional<Client> clientOptional = clientRepository.findByEmailIgnoreCase(mailParams.getEmail());
        if (clientOptional.isEmpty()) {
            Client client = clientRepository.findByChatId(mailParams.getChatId());
            client.setEmail(mailParams.getEmail());
            clientRepository.save(client);
            //send to mail_service for verification via rabbitMQ
            rabbitMQProducer.produceVerificationMail(mailParams);

        } else {
            //send message to tg_service about EMAIL_ALREADY_EXISTS
            AnswerMessage answerMessage = AnswerMessage.builder()
                    .chatId(mailParams.getChatId())
                    .message(EMAIL_ALREADY_EXISTS)
                    .build();
            emailAlreadyExistsMessage(answerMessage);
            log.info("Client found with email {}", mailParams.getEmail());
        }
    }

    private void emailAlreadyExistsMessage(AnswerMessage answerMessage) {
        rabbitMQProducer.produceAnswerMessage(answerMessage);
    }

    //подтверждение, что mail подтвержден
    public boolean getIsActive(Long chatId) {
        Client client = clientRepository.findByChatId(chatId);
        boolean isActive = false;
        if (client != null) {
            isActive = client.isActive();
        }
        return isActive;
    }

    public List<Client> getUsersByBirthday(int month, int day) {
        return clientRepository.findByMatchMonthAndMatchDay(month, day);
    }

    public ClientDto convertToDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .username(client.getUsername())
                .birthday(client.getBirthday())
                .phone(client.getPhone())
                .email(client.getEmail())
                .build();
    }

    public Client convertFromDto(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .username(clientDto.getUsername())
                .birthday(clientDto.getBirthday())
                .phone(clientDto.getPhone())
                .email(clientDto.getEmail())
                .build();
    }
}
