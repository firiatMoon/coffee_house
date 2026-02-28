package com.capybara.coffee_house.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

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

}
