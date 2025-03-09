package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ToString.Exclude
    private Category category;

    @Override
    public String toString() {
        return String.format("Product{name=%s, category=%s}", name, category.getTitle());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(name, product.name) &&
                Objects.equals(category.getTitle(), product.category.getTitle());
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(name, category.getTitle());
    }
}
