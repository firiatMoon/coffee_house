package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    @ToString.Exclude
    private Menu menu;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("OrderItem{Order=%s, Menu=%s, quantity=%s, price=%s}",
                order.getId(), menu.getId(), quantity, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem orderItem = (OrderItem) obj;
        return Objects.equals(order.getId(), orderItem.order.getId()) &&
                Objects.equals(menu.getId(), orderItem.menu.getId()) &&
                quantity.compareTo(orderItem.quantity) == 0 &&
                price.compareTo(orderItem.price) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(order.getId(), menu.getId(), quantity, price);
    }
}
