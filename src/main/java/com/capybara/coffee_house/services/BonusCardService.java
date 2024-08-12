package com.capybara.coffee_house.services;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import com.capybara.coffee_house.repositories.BonusCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusCardService {
    private final BonusCardRepository bonusCardRepository;

    @Autowired
    public BonusCardService(BonusCardRepository bonusCardRepository) {
        this.bonusCardRepository = bonusCardRepository;
    }

    public void save(BonusCard bonusCard) {
        bonusCardRepository.save(bonusCard);
    }

    public BonusCard getById(Long id) {
        return bonusCardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No BonusCard found with id"));
    }

    public BonusCard getByClientId(Long clientId) {
        return bonusCardRepository.findByClientId(clientId);
    }

    public BonusCard getByChatId(Long chatId) {
        return bonusCardRepository.findByChatId(chatId)
                .orElse(new BonusCard());
    }

    //передаем номер телефона, получаем инфу о бонус карте
    public BonusCard getByPhoneNumber(String phoneNumber){
        return bonusCardRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }
}
