package com.capybara.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;

    @Column(name = "title")
    private String title;

    @Override
    public String toString() {
        return String.format("Category{title=%s}", title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category category = (Category) obj;
        return Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(title);
    }
}
