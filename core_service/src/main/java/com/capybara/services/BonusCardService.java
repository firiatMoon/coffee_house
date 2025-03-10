package com.capybara.services;

import com.capybara.dto.ClientBonusCardDto;
import com.capybara.dto.ClientDto;
import com.capybara.entities.BonusCard;
import com.capybara.exceptions.EntityNotFoundException;
import com.capybara.repositories.BonusCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BonusCardService {
    private final BonusCardRepository bonusCardRepository;
    private final ClientService clientService;

    @Autowired
    public BonusCardService(BonusCardRepository bonusCardRepository, @Lazy ClientService clientService) {
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

    public String getByChatId(String chatId) {
        Long chatIdLong = Long.parseLong(chatId);
        BonusCard bonusCard = bonusCardRepository.findByChatId(chatIdLong)
                .orElse(new BonusCard());
        log.info("Send to core_service - {}", bonusCard.toString());
        return bonusCard.getAmount().toString();
    }

    public BonusCard getByChatId(Long chatId) {
        return bonusCardRepository.findByChatId(chatId)
                .orElse(new BonusCard());
    }

    public BonusCard getByClientId(Long clientId) {
        return bonusCardRepository.findByClientId(clientId)
                .orElseThrow(() -> new EntityNotFoundException("No bonus card found for this client"));
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
                .orElse(new ClientDto(null, null, null, null, null));
        return ClientBonusCardDto.builder()
                .id(bonusCard.getId())
                .client(clientDto)
                .amount(bonusCard.getAmount())
                .discountPercent(bonusCard.getDiscountPercent())
                .build();
    }
}
