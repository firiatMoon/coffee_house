package com.capybara.entities;

import com.capybara.enums.RegistrationState;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="telegram_bot")
public class TelegramBotInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "registration_state")
    @Enumerated(EnumType.STRING)
    private RegistrationState registrationState;

    @Override
    public String toString() {
        return String.format("TelegramBotInformation{chatId=%s, state=%s}", chatId, registrationState);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TelegramBotInformation telegramBotInformation = (TelegramBotInformation) obj;
        return Objects.equals(chatId, telegramBotInformation.chatId) &&
                registrationState == telegramBotInformation.registrationState;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(chatId, registrationState);
    }
}
