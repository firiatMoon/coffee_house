package com.capybara.coffee_house.entities;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
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
}
