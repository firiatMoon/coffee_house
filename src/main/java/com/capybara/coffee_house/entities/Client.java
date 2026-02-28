package com.capybara.coffee_house.entities;

import com.capybara.coffee_house.enums.RegistrationState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    @ToString.Exclude
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "is_active")
    @ToString.Exclude
    private boolean active;

    @Column(name = "chat_id")
    @ToString.Exclude
    private Long chatId;

    @Column(name = "registration_state")
    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    private RegistrationState registrationState;
}
