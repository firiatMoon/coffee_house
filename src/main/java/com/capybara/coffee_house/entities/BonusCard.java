package com.capybara.coffee_house.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "client_id", referencedColumnName = "id")
//    @ToString.Exclude
//    private Client client;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "discount_percent")
    private int discountPercent;
}
