package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.BonusCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BonusCardRepository extends JpaRepository<BonusCard, Long> {
    Optional<BonusCard> findByClientId(Long clientId);

    @Query(nativeQuery = true, value = """
            select bc.* 
            from bonus_card bc 
            join client c on bc.client_id = c.id 
            where c.chat_id = :chatId
            """
    )
    Optional<BonusCard> findByChatId(@Param("chatId") Long chatId);

    @Query(nativeQuery = true, value = """
            select bc.* 
            from bonus_card bc 
            join client c on bc.client_id = c.id 
            where c.phone = :phone
            """
    )
    Optional<BonusCard> findByPhoneNumber(@Param("phone") String phone);
}
