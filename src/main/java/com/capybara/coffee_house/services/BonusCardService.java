package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.ClientBonusCardDto;
import com.capybara.coffee_house.dto.ClientDto;
import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.BonusCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BonusCardService {
    private final BonusCardRepository bonusCardRepository;
    private final ClientService clientService;

    @Autowired
    public BonusCardService(BonusCardRepository bonusCardRepository, ClientService clientService) {
        this.bonusCardRepository = bonusCardRepository;
        this.clientService = clientService;
    }

    public void save(BonusCard bonusCard) {
        bonusCardRepository.save(bonusCard);
    }

    public BonusCard getById(Long id) {
        return bonusCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No BonusCard found with id"));
    }

    public BonusCard getByClientId(Long clientId) {
        return bonusCardRepository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("No bonus card found for this client"));
    }

    public BonusCard getByChatId(Long chatId) {
        return bonusCardRepository.findByChatId(chatId)
                .orElse(new BonusCard());
    }

    //передаем номер телефона, получаем инфу о бонус карте
    public BonusCard getByPhoneNumber(String phoneNumber){
         return bonusCardRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Bonus card for client  with phone: {} not found ", phoneNumber)));
    }

    public ClientBonusCardDto convertToDto(BonusCard bonusCard) {
        ClientDto clientDto = Optional.ofNullable(bonusCard.getClient())
                .map(clientService::convertToDto)
                .orElse(new ClientDto(null, null,null,null,null,null,null));
        return ClientBonusCardDto.builder()
                .id(bonusCard.getId())
                .client(clientDto)
                .amount(bonusCard.getAmount())
                .discountPercent(bonusCard.getDiscountPercent())
                .build();
    }
}
