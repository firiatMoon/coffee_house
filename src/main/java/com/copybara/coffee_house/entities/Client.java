package com.copybara.coffee_house.entities;

import com.copybara.coffee_house.enums.RegistrationState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate birthday;
    @Column(unique = true)
    private Long chatId;

    @Enumerated(EnumType.STRING)
    private RegistrationState registrationState;
}
