package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "menu_item")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ToString.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ToString.Exclude
    private Unit unit;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("Menu{product=%s, unit=%s, price=%s}", product.getName(), unit.getName(), price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Menu menu = (Menu) obj;
        return Objects.equals(product.getName(), menu.product.getName()) &&
                Objects.equals(unit.getName(), menu.unit.getName()) &&
                price.compareTo(menu.price) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(product.getName(), unit.getName(), price);
    }
}
