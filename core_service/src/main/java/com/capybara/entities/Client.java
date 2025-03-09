package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public String toString() {
        return String.format("Client{username=%s, birthday=%s, phone=%s, email=%s, chat_id=%s}",
                username, birthday, phone, email, chatId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return Objects.equals(username, client.username) &&
                Objects.equals(birthday, client.birthday) &&
                Objects.equals(phone, client.phone) &&
                Objects.equals(email, client.email) &&
                Objects.equals(chatId, client.chatId);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(username, birthday, phone, email, chatId);
    }
}
