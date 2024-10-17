package com.capybara.coffee_house.repositories;

import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.enums.RegistrationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByChatId(Long chatId);
    Optional<Client> findByEmailIgnoreCase(String email);
    Optional<Client> findByPhone(String phoneNumber);




    @Query(value = "SELECT * FROM client " +
            "WHERE email IS NOT NULL " +
            "AND extract(MONTH FROM birthday) = :m " +
            "AND extract(DAY FROM birthday) = :d",
            nativeQuery = true)
    List<Client> findByMatchMonthAndMatchDay(@Param("m") int month, @Param("d") int day);
}
