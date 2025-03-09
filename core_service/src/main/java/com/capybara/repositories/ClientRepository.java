package com.capybara.repositories;

import com.capybara.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByChatId(Long chatId);
    Optional<Client> findByEmailIgnoreCase(String email);
    Optional<Client> findByPhone(String phoneNumber);




    @Query(value = "select * from client " +
            "where email IS NOT NULL " +
            "and extract(MONTH FROM birthday) = :m " +
            "and extract(DAY FROM birthday) = :d",
            nativeQuery = true)
    List<Client> findByMatchMonthAndMatchDay(@Param("m") int month, @Param("d") int day);
}
