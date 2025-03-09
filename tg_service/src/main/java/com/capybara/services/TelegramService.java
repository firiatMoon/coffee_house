package com.capybara.services;

import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.repositories.TelegramRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TelegramService{
    private final TelegramRepository telegramRepository;

    public TelegramService(TelegramRepository telegramRepository) {
        this.telegramRepository = telegramRepository;
    }

    public Optional<TelegramBotInformation> getByChatId(Long chatId) {
        return telegramRepository.findByChatId(chatId);
    }

    public void saveRegistrationState(Long chatId) {
        Optional<TelegramBotInformation> telegramBotInformation = telegramRepository.findByChatId(chatId);
        if(telegramBotInformation.isPresent()) {
            TelegramBotInformation telegramBotInformationInfo = telegramBotInformation.get();
            telegramBotInformationInfo.setRegistrationState(RegistrationState.REGISTERED);
            telegramRepository.save(telegramBotInformationInfo);
        } else {
            throw new EntityNotFoundException("Client not found");
        }
    }
}
