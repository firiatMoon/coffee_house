package com.capybara.entities;

import com.capybara.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
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

    @Override
    public String toString() {
        return String.format("ClientBonusCardTransaction{Order=%s, TransactionType=%s, points=%s, transactionDate=%s}",
                order.getId(), transactionType, points, transactionDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClientBonusCardTransaction clientBonusCardTransaction = (ClientBonusCardTransaction) obj;
        return Objects.equals(order.getId(), clientBonusCardTransaction.order.getId()) &&
               transactionType == clientBonusCardTransaction.transactionType &&
                points.compareTo(clientBonusCardTransaction.points) == 0 &&
                transactionDate.isEqual(clientBonusCardTransaction.transactionDate);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(order.getId(), transactionType, points, transactionDate);
    }
}
