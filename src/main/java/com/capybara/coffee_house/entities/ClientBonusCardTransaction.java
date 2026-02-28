package com.capybara.coffee_house.entities;

import com.capybara.coffee_house.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Entity
@Table(name = "client_bonus_card_transaction")
public class ClientBonusCardTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ToString.Exclude
    private Order order;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "points")
    private BigDecimal points;

    @Column(name = "transaction_date")
    @CreationTimestamp
    @ToString.Exclude
    private LocalDateTime transactionDate;
}
