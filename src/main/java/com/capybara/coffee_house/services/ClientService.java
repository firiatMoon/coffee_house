package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.ClientDto;
import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BonusCardService bonusCardService;

    @Autowired
    public ClientService(ClientRepository clientRepository, BonusCardService bonusCardService) {
        this.clientRepository = clientRepository;
        this.bonusCardService = bonusCardService;
    }

    public Optional<Client> getByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void createClient(Client client) {
        Client savedClient = clientRepository.save(client);
        BonusCard bonusCard = new BonusCard();
        bonusCard.setClient(savedClient);
        bonusCard.setAmount(BigDecimal.valueOf(0.0));
        bonusCardService.save(bonusCard);
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
                .chatId(client.getChatId())
                .registrationState(client.getRegistrationState())
                .build();
    }

    public Client convertFromDto(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .username(clientDto.getUsername())
                .birthday(clientDto.getBirthday())
                .phone(clientDto.getPhone())
                .email(clientDto.getEmail())
                .registrationState(clientDto.getRegistrationState())
                .build();
    }
}
