package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByChatId(Long chatId);
}
