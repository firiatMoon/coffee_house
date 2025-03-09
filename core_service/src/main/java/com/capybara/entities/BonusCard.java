package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "bonus_card")
public class BonusCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    @ToString.Exclude
    private Client client;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "discount_percent")
    private int discountPercent;

    @Override
    public String toString() {
        return String.format("BonusCard{client=%s, amount=%s, discountPercent=%s}", client.getUsername()
                , amount, discountPercent);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BonusCard bonusCard = (BonusCard) obj;
        return Objects.equals(client.getUsername(), bonusCard.client.getUsername()) &&
                amount.compareTo(bonusCard.amount) == 0 &&
                discountPercent == bonusCard.discountPercent;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(client.getUsername(), amount, discountPercent);
    }
}
