package com.capybara.repositories;

import com.capybara.entities.TelegramBotInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramRepository extends JpaRepository<TelegramBotInformation, Long> {
    Optional<TelegramBotInformation> findByChatId(Long chatId);
}
