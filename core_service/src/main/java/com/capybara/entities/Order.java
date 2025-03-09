package com.capybara.entities;

import com.capybara.enums.BonusPointsAction;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ToString.Exclude
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Column(name = "bonus_points_action")
    @Enumerated(EnumType.STRING)
    private BonusPointsAction bonusPointsAction;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;

    @Column(name = "created_at")
    @ToString.Exclude
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return String.format("Order{Client=%s, User=%s, BonusPointsAction=%s, orderItems=%s, totalAmount=%s, " +
                        "discountAmount=%s, finalAmount=%s, createdAt=%s}",
                client.getId(), user.getId(), bonusPointsAction, orderItems, totalAmount, discountAmount, finalAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return Objects.equals(client.getId(), order.client.getId()) &&
                Objects.equals(user.getId(), order.user.getId()) &&
                bonusPointsAction == order.bonusPointsAction &&
                Objects.equals(orderItems, order.orderItems) &&
                totalAmount.compareTo(order.totalAmount) == 0 &&
                discountAmount.compareTo(order.discountAmount) == 0 &&
                finalAmount.compareTo(order.finalAmount) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(client.getId(), user.getId(), bonusPointsAction, orderItems, totalAmount,
                discountAmount, finalAmount);
    }
}
