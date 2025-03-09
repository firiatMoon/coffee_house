package com.capybara.repositories;

import com.capybara.entities.ClientBonusCardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientBonusCardTransactionRepository extends JpaRepository<ClientBonusCardTransaction, Long> {
}
