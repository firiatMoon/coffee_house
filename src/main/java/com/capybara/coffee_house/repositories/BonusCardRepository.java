package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.BonusCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusCardRepository extends JpaRepository<BonusCard, Long> {
    BonusCard findByClientId(Long clientId);
}
