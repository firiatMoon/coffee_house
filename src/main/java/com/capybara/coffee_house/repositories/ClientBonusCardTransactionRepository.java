package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.ClientBonusCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientBonusCardTransactionRepository extends JpaRepository<ClientBonusCardTransaction, Long> {
}
