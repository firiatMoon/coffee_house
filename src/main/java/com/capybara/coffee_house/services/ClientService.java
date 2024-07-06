package com.capybara.coffee_house.services;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getByChatId(Long chatId) {
        return clientRepository.findByChatId(chatId);
    }

    public void save(Client client) {
        clientRepository.save(client);
    }

}
